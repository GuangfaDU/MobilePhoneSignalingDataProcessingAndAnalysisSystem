package cn.edu.szu.algo.interfaces.density;

import cn.edu.szu.algo.entity.BaseStation;
import cn.edu.szu.algo.entity.SignalData;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface IComputeDensity {

    /**
     * 计算所有基站不同时刻的人群密度并保存到MongoDB
     * @param signalData 统计的数据
     */
    void computeDensity(SignalData signalData);
}
