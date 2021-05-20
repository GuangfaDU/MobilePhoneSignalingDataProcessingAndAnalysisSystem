package cn.edu.szu.algo.entity;

import cn.edu.szu.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("signal_data")
public class SignalData extends BaseEntity {

    /**
     * 加密后的用户ID
     */
    @TableField("imsi")
    private String imsi;
    /**
     * 开始时间
     */
    @TableField("start")
    private String start;
    /**
     * 结束时间
     */
    @TableField("end")
    private String end;
    /**
     * 经度
     */
    @TableField("longitude")
    @JsonProperty("lng")
    private Double longitude;
    /**
     * 纬度
     */
    @TableField("latitude")
    @JsonProperty("lat")
    private Double latitude;
}
