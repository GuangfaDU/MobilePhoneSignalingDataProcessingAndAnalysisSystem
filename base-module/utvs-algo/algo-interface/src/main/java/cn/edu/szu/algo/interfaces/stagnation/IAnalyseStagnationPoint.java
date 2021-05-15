package cn.edu.szu.algo.interfaces.stagnation;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface IAnalyseStagnationPoint {

    /**
     * 默认的文件缓存目录
     */
    String DEFAULT_WORD_DIR = "D:/GuangfaDU/data/temp/python/";

    /**
     * default python function dir
     */
    String DEFAULT_PYTHON_MODULE_DIR = "D:/GuangfaDU/Code/python_module/main.py";

    /**
     * 根据驻留点的提取结果进行聚类分析
     */
    void analyzeStagnationPoint();
}
