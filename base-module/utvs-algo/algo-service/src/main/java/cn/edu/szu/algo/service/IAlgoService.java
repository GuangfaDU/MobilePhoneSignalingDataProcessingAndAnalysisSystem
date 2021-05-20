package cn.edu.szu.algo.service;

import cn.edu.szu.algo.entity.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface IAlgoService {

    /**
     * 返回给定时间段的所有出行轨迹
     * @param startTime 开始时间段
     * @param endTime 结束时间段
     * @return 所有用户出行轨迹的抽象表示
     */
    List<TravelTrajectoryResultMap> getAllTravelTrajectory(String startTime, String endTime);

    /**
     * 返回给定时间段的所有基站人群密度
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 所有基站密度的抽象表示
     */
    List<DensityResultMap> getDensity(String startTime, String endTime);

    /**
     * 返回给定时间段的所有用户驻留点
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 所有用户驻留点的抽象表示
     */
    List<StagnationResultMap> getStagnation(String startTime, String endTime);

    /**
     * 获取用户高频活动地点
     * @return 用户高频活动地点列表
     */
    List<ClusteringResultMap> getPoi();
}
