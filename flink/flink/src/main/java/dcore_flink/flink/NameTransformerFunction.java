package dcore_flink.flink;

import dcore_flink.event.DemoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.api.common.functions.MapFunction;

public class NameTransformerFunction implements MapFunction<DemoEvent, DemoEvent> {
    private static final Logger logger = LoggerFactory.getLogger(NameTransformerFunction.class);
    @Override
    public DemoEvent map(DemoEvent event) {
        logger.info("Converting event name from {} to {}", event.getName(), event.getName().toUpperCase());
        event.setName(event.getName().toUpperCase());
        return event;
    }
}