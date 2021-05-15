package cn.edu.szu.file.service;

import cn.edu.szu.file.entity.CustomFile;
import cn.edu.szu.file.util.HdfsTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private HdfsTemplate hdfsTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRemoteFile(MultipartFile multipartFile) {
        String saveLocalPath = LOCAL_UPLOAD_FILE_PATH + multipartFile.getOriginalFilename();
        try {
            File file = new File(saveLocalPath);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return false;
                }
            }
            multipartFile.transferTo(file);
            hdfsTemplate.copyFileToHdfs(saveLocalPath, true, true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveLocalFile(String fileName) {
        try {
            hdfsTemplate.copyFileToHdfs(LOCAL_UPLOAD_FILE_PATH + fileName, true, true);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public void downloadFileToClient(String fileName, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(fileName)) {
            return;
        }

        File file = new File(LOCAL_DOWNLOAD_FILE_PATH);
        File[] files = file.listFiles();
        assert files != null;
        for (File item : files) {
            if (item.getName().equals(fileName)) {
                downloadFromLocal(fileName, request, response);
                return;
            }
        }

        downloadFromRemote(fileName, request, response);
    }

    @Override
    public boolean downloadFileToLocal(String fileName) {
       try {
           hdfsTemplate.copyFromHdfs(fileName, LOCAL_DOWNLOAD_FILE_PATH);
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }

       return true;
    }

    @Override
    public List<CustomFile> listFile(String srcPath) {
        return hdfsTemplate.listFiles(srcPath);
    }

    @Override
    public boolean deleteFile(String fileName) {
        return hdfsTemplate.rm(fileName);
    }

    private void downloadFromLocal(String fileName, HttpServletRequest request, HttpServletResponse response) {
        File file = new File(LOCAL_DOWNLOAD_FILE_PATH + fileName);
        long startByte = 0L, endByte = file.length() - 1;

        String range = request.getHeader("Range");
        if (StringUtils.isEmpty(range) && !range.contains("bytes=") && !range.contains("-")) {
            return;
        }

        range = range.substring(range.indexOf("=") + 1).trim();
        String[] ranges = range.split("-");
        if (ranges.length == 1 && range.startsWith("-")) {
            endByte = Long.parseLong(ranges[0]);
        }
        if (ranges.length == 1 && range.endsWith("-")) {
            startByte = Long.parseLong(ranges[0]);
        }
        if (ranges.length == 2) {
            startByte = Long.parseLong(ranges[0]);
            endByte = Long.parseLong(ranges[1]);
        }

        long contentLength = endByte - startByte + 1;

        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setHeader("Content-Length", String.valueOf(contentLength));
        response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + file.length());
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        response.setContentType("charset=UTF-8");

        BufferedOutputStream outputStream;
        RandomAccessFile randomAccessFile = null;

        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(startByte);
            outputStream = new BufferedOutputStream(response.getOutputStream());

            byte[] buffer = new byte[4096];
            long transmittedLength = 0L;
            int readLength = 0;
            while ((transmittedLength + readLength) < contentLength &&
                    (readLength = randomAccessFile.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readLength);
                transmittedLength += readLength;
            }

            if (transmittedLength < contentLength) {
                readLength = randomAccessFile.read(buffer, 0, (int) (contentLength - transmittedLength));
                outputStream.write(buffer, 0, readLength);
                transmittedLength += readLength;
            }

            outputStream.flush();
            response.flushBuffer();
            randomAccessFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void downloadFromRemote(String fileName, HttpServletRequest request, HttpServletResponse response) {
        hdfsTemplate.copyFromHdfs(fileName, LOCAL_DOWNLOAD_FILE_PATH);
        downloadFromLocal(fileName, request, response);
    }
}
