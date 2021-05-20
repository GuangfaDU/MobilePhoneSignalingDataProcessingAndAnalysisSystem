package cn.edu.szu.algo.interfaces.pretreatment;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface IImportData {

    /**
     * 把HDFS里文件形式的信令数据导入到数据库进行高速查询
     */
    void importData();
}
