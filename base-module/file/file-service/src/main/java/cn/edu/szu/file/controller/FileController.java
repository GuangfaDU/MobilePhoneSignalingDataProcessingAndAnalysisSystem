package cn.edu.szu.file.controller;

import cn.edu.szu.entity.ResponseEntity;
import cn.edu.szu.file.service.IFileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RestController
public class FileController {

    @Autowired
    private IFileService fileService;

    @PostMapping("/upload/remote")
    public ResponseEntity uploadRemoteFile(@RequestParam("file") MultipartFile multipartFile) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (multipartFile.isEmpty()) {
            return responseEntity.setSuccess(false).setMessage("文件上传失败, 请重试");
        }

        if (fileService.saveRemoteFile(multipartFile)) {
            return responseEntity.setSuccess(true).setMessage("文件上传成功");
        }

        return responseEntity.setSuccess(false).setMessage("文件上传失败, 请重试");
    }

    @PostMapping("/upload/local")
    public ResponseEntity uploadLocalFile(@RequestBody String fileName) {
        if (fileService.saveLocalFile(fileName)) {
            return new ResponseEntity().setSuccess(true).setMessage("本地文件上传成功");
        }

        return new ResponseEntity().setSuccess(false).setMessage("本地文件上传失败");
    }

    @GetMapping("/download/remote/{fileName}")
    public void downloadToClient(@PathVariable("fileName") String fileName,
                                 HttpServletRequest request, HttpServletResponse response) {
        fileService.downloadFileToClient(fileName, request, response);
    }

    @GetMapping("/download/local/{fileName}")
    public ResponseEntity downloadToLocal(@PathVariable("fileName") String fileName) {
        if (fileService.downloadFileToLocal(fileName)) {
            return new ResponseEntity().setSuccess(true).setMessage("本地文件拉取完成");
        }

        return new ResponseEntity().setSuccess(false).setMessage("本地文件拉取失败");
    }

    @GetMapping("/list/{path}")
    public ResponseEntity listFile(@PathVariable("path") String srcPath) {
        ResponseEntity entity = new ResponseEntity();
        if (StringUtils.isEmpty(srcPath)) {
            return entity.setSuccess(true).setData(fileService.listFile("/utvs"));
        }
        return entity.setSuccess(true).setData(fileService.listFile(srcPath));
    }

    @PostMapping("/delete/{fileName}")
    public ResponseEntity deleteFile(@PathVariable("fileName") String fileName) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(fileName)) {
            return responseEntity.setSuccess(false).setMessage("文件名不能为空");
        }

        if (!fileService.deleteFile(fileName)) {
            return responseEntity.setSuccess(false).setMessage("文件不存在");
        }

        return responseEntity.setSuccess(true).setMessage("删除成功");
    }
}
