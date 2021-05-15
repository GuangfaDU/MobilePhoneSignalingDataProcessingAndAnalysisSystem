package cn.edu.szu.algo.component.pretreatment;

import cn.edu.szu.algo.client.FileServiceClient;
import cn.edu.szu.algo.entity.CustomFile;
import cn.edu.szu.algo.interfaces.DefaultParams;
import cn.edu.szu.algo.interfaces.pretreatment.IBaseUtil;
import cn.edu.szu.entity.ResponseEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public class BaseUtilImpl implements DefaultParams, IBaseUtil {

    protected final FileServiceClient fileServiceClient;
    protected final ObjectMapper objectMapper;

    public BaseUtilImpl(FileServiceClient fileServiceClient, ObjectMapper objectMapper) {
        this.fileServiceClient = fileServiceClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public HashSet<String> getFileList() {
        ResponseEntity responseEntity = fileServiceClient.listFile(DEFAULT_HANDLE_DIRECTORY);
        List<CustomFile> fileList = objectMapper.convertValue(
                responseEntity.getData(), new TypeReference<LinkedList<CustomFile>>() {
                });
        HashSet<String> fileNameSet = new HashSet<>();
        fileList.forEach(customFile -> fileNameSet.add(customFile.getFileName()));
        return fileNameSet;
    }

    @Override
    public void ensureFileExists(String fileName) {
        File file = new File(DEFAULT_DOWNLOAD_DIRECTORY + fileName);
        if (!file.exists()) {
            fileServiceClient.downloadFile(fileName);
        }
    }

    @Override
    public void uploadFile(String fileName) {
        fileServiceClient.uploadFile(fileName);
    }
}
