package dcore_database.flink;

import dcore_database.proto.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        logger.info("Flink job finished");
    }
}
