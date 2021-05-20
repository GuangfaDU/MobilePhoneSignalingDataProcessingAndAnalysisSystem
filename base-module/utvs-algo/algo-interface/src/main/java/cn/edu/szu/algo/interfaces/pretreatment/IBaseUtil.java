package cn.edu.szu.algo.interfaces.pretreatment;

import java.util.HashSet;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface IBaseUtil {

    /**
     * 获取HDFS的文件列表
     * @return 文件列表
     */
    HashSet<String> getFileList();

    /**
     * 检查文件是否存在本地，可能需要从远程HDFS服务器拉去
     * @param fileName 文件名称
     */
    void ensureFileExists(String fileName);

    /**
     * 上传本地文件到远程HDFS服务器
     * @param fileName 文件名称
     */
    void uploadFile(String fileName);
}
