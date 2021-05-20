package cn.edu.szu.algo.client;

import cn.edu.szu.entity.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@FeignClient("file-service")
public interface FileServiceClient {

    /**
     * 获取文件列表
     * @param srcPath 路径
     * @return 文件列表
     */
    @GetMapping("/list/{path}")
    ResponseEntity listFile(@PathVariable("path") String srcPath);

    /**
     * 下载文件
     * @param fileName 文件名称
     * @return 调用信息
     */
    @GetMapping("/download/local/{fileName}")
    String downloadFile(@PathVariable("fileName") String fileName);

    /**
     * 上传文件
     * @param fileName 文件名称
     * @return 调用信息
     */
    @PostMapping("/upload/local")
    String uploadFile(@RequestBody String fileName);
}
