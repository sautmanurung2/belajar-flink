package dcore_flink.flink;

import dcore_flink.event.DemoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

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
                factory.kafkaSource(SOURCE_TOPIC),
                factory.kafkaSink(SINK_TOPIC));
        logger.info("Flink job finished");
    }

    protected static void execute(StreamExecutionEnvironment environment, KafkaSource<DemoEvent> kafkaSource, KafkaSink<DemoEvent> kafkaSink) {
        try {
            DataStream<DemoEvent> eventInputStream = environment.fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "Kafka Source");
            eventInputStream.map(new NameTransformerFunction()).sinkTo(kafkaSink);
            environment.execute("EventProcessor Job");
        } catch (Exception e) {
            logger.error("Failed to execute Flink job", e);
            throw new RuntimeException("Failed to execute Flink job", e);
        }
    }
}