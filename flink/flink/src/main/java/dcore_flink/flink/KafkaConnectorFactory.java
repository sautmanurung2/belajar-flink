package dcore_flink.flink;

import dcore_flink.event.DemoEvent;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.formats.json.JsonSerializationSchema;

public class KafkaConnectorFactory {
    private final String bootstrapServers;

    public KafkaConnectorFactory(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public KafkaSource<DemoEvent> kafkaSource(String sourceTopic) {
        return KafkaSource.<DemoEvent>builder()
                .setBootstrapServers(bootstrapServers)
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(DemoEvent.class))
                .setTopics(sourceTopic)
                .build();
    }

    public KafkaSink<DemoEvent> kafkaSink(String sinkTopic) {
        return KafkaSink.<DemoEvent>builder()
                .setBootstrapServers(bootstrapServers)
                .setRecordSerializer(
                        KafkaRecordSerializationSchema.<DemoEvent>builder()
                                .setValueSerializationSchema(new JsonSerializationSchema<>())
                                .setTopic(sinkTopic)
                                .build())
                .build();
    }
}