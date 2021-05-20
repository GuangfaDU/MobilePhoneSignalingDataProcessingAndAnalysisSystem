package cn.edu.szu.algo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Document(collection = "clustering")
public class ClusteringResultMap {

    @Id
    private String id;

    @Indexed(unique = true)
    private String imsi;
    private List<Point> poi;

    @PersistenceConstructor
    public ClusteringResultMap(String imsi, List<Point> poi) {
        this.imsi = imsi;
        this.poi = poi;
    }

    public static class Point {
        @JsonProperty("lng")
        public Double longitude;
        @JsonProperty("lat")
        public Double latitude;
        public String description;
    }
}
