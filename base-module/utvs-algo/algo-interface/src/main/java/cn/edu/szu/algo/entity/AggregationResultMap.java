package cn.edu.szu.algo.entity;

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
@Document(collection = "signal_data")
public class AggregationResultMap {

    @Id
    private String id;

    @Indexed(unique = true)
    private String imsi;

    private List<SignalData> signalDataList;

    @PersistenceConstructor
    public AggregationResultMap(String imsi, List<SignalData> signalDataList) {
        this.imsi = imsi;
        this.signalDataList = signalDataList;
    }
}
