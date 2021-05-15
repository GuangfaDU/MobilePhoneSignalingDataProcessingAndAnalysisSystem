package cn.edu.szu.algo.dao;

import cn.edu.szu.algo.entity.SignalData;
import cn.edu.szu.dao.ICrudDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface SignalDataDao extends ICrudDao<SignalData> {

    /**
     * 批量插入数据
     * @param signalDataList 信令数据
     * @return 受影响的行数
     */
    int insertBatch(@Param("signalDataList") List<SignalData> signalDataList);
}
