package dcore_database.tools;

import dcore_database.proto.EventStore;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EventStoreDeserializer implements DeserializationSchema<EventStore> {
    private static final Logger logger = LoggerFactory.getLogger(EventStoreDeserializer.class);

    @Override
    public EventStore deserialize(byte[] message) throws IOException {
        try {
            logger.debug("Deserializing message: {}", new String(message)); // Use debug level for large outputs
            return EventStore.parseFrom(message);
        } catch (Exception e) {
            logger.error("Failed to deserialize message", e);
            throw new IOException("Failed to deserialize message", e);
        }
    }

    @Override
    public boolean isEndOfStream(EventStore eventStore) {
        return false; // Return true if the stream ends
    }

    @Override
    public TypeInformation<EventStore> getProducedType() {
        return TypeInformation.of(EventStore.class);
    }
}
