import cn.edu.szu.algo.entity.CustomFile;
import cn.edu.szu.entity.ResponseEntity;
import cn.edu.szu.algo.AlgoServiceApplication;
import cn.edu.szu.algo.client.FileServiceClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlgoServiceApplication.class)
public class TestRemoteFileService {

    @Autowired
    FileServiceClient fileServiceClient;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testListFile() {
        ResponseEntity responseEntity = fileServiceClient.listFile("/utvs");
        LinkedList<CustomFile> fileList = objectMapper.convertValue(
                responseEntity.getData(), new TypeReference<LinkedList<CustomFile>>() {});
        HashSet<String> fileNameSet = new HashSet<>();
        for (CustomFile customFile : fileList) {
            fileNameSet.add(customFile.getFileName());
        }
        System.out.println(fileNameSet);
    }

    @Test
    public void testDownloadFile() {
        System.out.println(fileServiceClient.downloadFile("extra01.csv"));
    }

    @Test
    public void testUploadFile() {
        System.out.println(fileServiceClient.uploadFile("extra01-pretreated.csv"));
    }
}
