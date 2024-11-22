package dcore_database.flink;

import dcore_database.proto.EventStore;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;

public class KafkaConnectorFactory {
    private final String bootstrapServers;

    public KafkaConnectorFactory(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public KafkaSource<EventStore> kafkaSource(String sourceTopic) {
        return KafkaSource.<EventStore>builder()
                .setBootstrapServers(bootstrapServers)
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(EventStore.class))
                .setTopics(sourceTopic)
                .build();
    }
}