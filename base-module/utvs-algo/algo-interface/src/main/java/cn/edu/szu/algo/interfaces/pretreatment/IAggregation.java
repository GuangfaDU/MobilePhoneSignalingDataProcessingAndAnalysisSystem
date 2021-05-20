package cn.edu.szu.algo.interfaces.pretreatment;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface IAggregation {

    /**
     * 从MySQL数据库获取数据，
     * 按IMSI分组聚合信令数据保存到MongoDB
     */
    void aggregation();
}
