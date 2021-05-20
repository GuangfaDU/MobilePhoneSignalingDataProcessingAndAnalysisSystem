package cn.edu.szu.algo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class Point {

    @JsonProperty("lng")
    private Double longitude;
    @JsonProperty("lat")
    private Double latitude;
}
