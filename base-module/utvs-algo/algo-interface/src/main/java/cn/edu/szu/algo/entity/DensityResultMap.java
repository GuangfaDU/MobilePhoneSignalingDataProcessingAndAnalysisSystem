package cn.edu.szu.algo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@Document("density")
@NoArgsConstructor
public class DensityResultMap {

    @Id
    @JsonIgnore
    private String id;

    @JsonProperty("lng")
    private Double longitude;
    @JsonProperty("lat")
    private Double latitude;
    @JsonIgnore
    private String startTime;
    @JsonIgnore
    private String endTime;
    @JsonProperty("count")
    private Long density;

    @PersistenceConstructor
    public DensityResultMap(Double longitude, Double latitude, String startTime, String endTime, Long density) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.startTime = startTime;
        this.endTime = endTime;
        this.density = density;
    }
}
