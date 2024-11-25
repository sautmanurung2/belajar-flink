package dcore_database.flink;

import dcore_database.proto.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;

public class EventProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EventProcessor.class);
    private static final String BOOTSTRAP_SERVERS = "kafka-0:9092";
    private static final String SOURCE_TOPIC = "kafka-testing-producer";
    private static final String SINK_TOPIC = "kafka-testing-consumer";

    public static void main(String[] args) {
        String bootstrapServers = BOOTSTRAP_SERVERS;

        if (args.length > 0) {
            bootstrapServers = args[0];
        }
        logger.info("Starting Flink job with bootstrap servers {}", bootstrapServers);

        logger.info("Flink job starting...");
        KafkaConnectorFactory factory = new KafkaConnectorFactory(bootstrapServers);
        execute(StreamExecutionEnvironment.getExecutionEnvironment(),
                factory.kafkaSource(SOURCE_TOPIC));

        logger.info("Flink job finished");
    }

    protected static void execute(StreamExecutionEnvironment environment, KafkaSource<EventStore> kafkaSource) {
        try {
            DataStreamSource<EventStore> eventInputStream = environment.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source");
            eventInputStream.addSink(JdbcSink.sink("insert into public.event_store(event_store_id, event_name, item_number, item_type, user_id, user_login, node_id, node_code, created_at) values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                (statement, event) -> {
                    statement.setString(1, event.getEventStoreId());
                    statement.setString(2, event.getEventName());
                    statement.setString(3, event.getItemNumber());
                    statement.setString(4, event.getItemType());
                    statement.setInt(5, event.getUserId());
                    statement.setString(6, event.getUserLogin());
                    statement.setInt(7, event.getNodeId());
                    statement.setString(8, event.getNodeCode());
                    statement.setTimestamp(9, new java.sql.Timestamp(event.getCreatedAt().getSeconds() * 1000 + event.getCreatedAt().getNanos() / 1_000_000));
                },
                JdbcExecutionOptions.builder()
                    .withBatchSize(1000)
                    .withBatchIntervalMs(200)
                    .withMaxRetries(5)
                    .build(),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                    .withUrl("jdbc:postgresql://flink-docker-postgres-1:5432/postgres")
                    .withDriverName("org.postgresql.Driver")
                    .withUsername("admin")
                    .withPassword("admin")
                    .build()
            ));
            environment.execute("Kafka to PostgreSQL");
        } catch (Exception e) {
            logger.error("Failed to execute Flink job", e);
            throw new RuntimeException("Failed to execute Flink job", e);
        }
    }
}
