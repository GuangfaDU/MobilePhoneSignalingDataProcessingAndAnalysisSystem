package cn.edu.szu.algo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("station")
public class BaseStation {

    @Id
    private String id;
    @EqualsAndHashCode.Include
    private Double longitude;
    @EqualsAndHashCode.Include
    private Double latitude;

    @PersistenceConstructor
    public BaseStation(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
