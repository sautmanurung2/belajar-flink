package dcore_flink.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonSerialize
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoEvent {
    private String name;
}