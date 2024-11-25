package dcore_database.flink;

import dcore_database.proto.EventStore;
import org.apache.flink.connector.kafka.source.KafkaSource;
import dcore_database.tools.EventStoreDeserializer;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import dcore_database.tools.EventStoreSerializer;

public class KafkaConnectorFactory {
    private final String bootstrapServers;

    public KafkaConnectorFactory(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public KafkaSource<EventStore> kafkaSource(String sourceTopic) {
        return KafkaSource.<EventStore>builder()
                .setBootstrapServers(bootstrapServers)
                .setValueOnlyDeserializer(new EventStoreDeserializer())
                .setTopics(sourceTopic)
                .build();
    }

    public KafkaSink<EventStore> kafkaSink(String sinkTopic) {
        return KafkaSink.<EventStore>builder()
                .setBootstrapServers(bootstrapServers)
                .setRecordSerializer(KafkaRecordSerializationSchema.<EventStore>builder()
                                .setValueSerializationSchema(new EventStoreSerializer())
                                .setTopic(sinkTopic)
                                .build())
                .build();
    }
}