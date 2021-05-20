package cn.edu.szu.algo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@Document(collection = "trajectory")
public class TravelTrajectoryResultMap {

    @Id
    private String id;

    private String imsi;

    private String startTime;

    private String endTime;

    private Point startPoint;

    private Point endPoint;

    private LinkedList<SignalData> pointList;

    @PersistenceConstructor
    public TravelTrajectoryResultMap(String imsi, String startTime, String endTime,
                                     Point startPoint, Point endPoint, LinkedList<SignalData> pointList) {
        this.imsi = imsi;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.pointList = pointList;
    }
}
