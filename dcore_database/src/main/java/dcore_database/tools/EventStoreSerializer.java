package dcore_database.tools;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dcore_database.proto.EventStore;

public class EventStoreSerializer implements SerializationSchema<EventStore> {
    private static final Logger logger = LoggerFactory.getLogger(EventStoreSerializer.class);

    @Override
    public byte[] serialize(EventStore eventStore) {
        byte[] serializedData = eventStore.toByteArray();
        logger.debug("Serialized EventStore: length = {}", serializedData.length); // Log the size of the serialized data
        return serializedData;
    }
}
