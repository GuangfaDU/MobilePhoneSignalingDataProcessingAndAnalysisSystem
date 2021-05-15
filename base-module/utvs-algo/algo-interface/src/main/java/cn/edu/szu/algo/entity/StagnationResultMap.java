package cn.edu.szu.algo.entity;

import lombok.Data;
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
@Document("stagnation")
public class StagnationResultMap {

    @Id
    private String id;

    private String imsi;
    private String startTime;
    private String endTime;
    private Point stagnationPoint;

    @PersistenceConstructor
    public StagnationResultMap(String imsi, String startTime, String endTime, Point stagnationPoint) {
        this.imsi = imsi;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stagnationPoint = stagnationPoint;
    }
}
