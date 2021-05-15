package cn.edu.szu.file.service;

import cn.edu.szu.file.entity.CustomFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public interface IFileService {

    /**
     * 上传文件本地缓存
     */
    String LOCAL_UPLOAD_FILE_PATH = "D:/GuangfaDU/data/temp/upload/";
    /**
     * 下载文件本地缓存
     */
    String LOCAL_DOWNLOAD_FILE_PATH = "D:/GuangfaDU/data/temp/download/";

    /**
     * 把用户上传的文件保存到本地，本地再上传到HDFS
     *
     * @param multipartFile 用户上传的文件
     * @return 保存文件是否成成功
     */
    boolean saveRemoteFile(MultipartFile multipartFile);

    /**
     * 上传本地文件
     * @param fileName 文件名称
     * @return 上传文件是否成功
     */
    boolean saveLocalFile(String fileName);

    /**
     * 下载文件：
     * 1. 查询本地缓存若存在直接传输
     * 2. 从HDFS获取到本地缓存再进行传输
     *
     * @param fileName 文件名称
     * @param request  req
     * @param response resp
     */
    void downloadFileToClient(String fileName, HttpServletRequest request, HttpServletResponse response);

    /**
     * 服务内部调用，下载至本地服务器
     * @param fileName 文件名称
     * @return 下载是否成功
     */
    boolean downloadFileToLocal(String fileName);

    /**
     * 查询某个路径下的文件
     *
     * @param srcPath 文件路径
     * @return 该路径下的所有文件列表
     */
    List<CustomFile> listFile(String srcPath);

    /**
     * 删除指定文件
     * @param fileName 需要被删除的文件名称
     * @return 删除是否成功
     */
    boolean deleteFile(String fileName);
}
