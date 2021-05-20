import cn.edu.szu.file.FileApplication;
import cn.edu.szu.file.service.IFileService;
import cn.edu.szu.file.util.HdfsTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileApplication.class)
public class HdfsTemplateTest {

    @Autowired
    private HdfsTemplate hdfsTemplate;

    @Test
    public void testExist() {
        String filePath = "/utvs";
        System.out.println(hdfsTemplate.existDir(filePath, false));;
    }

    @Test
    public void testUpload() {
        String srcFilePath = "D:\\GuangfaDU\\data\\temp\\extra01.csv";
        hdfsTemplate.copyFileToHdfs(srcFilePath, true, true);
    }

    @Test
    public void testDownload() {
        hdfsTemplate.copyFromHdfs("extra01.csv", IFileService.LOCAL_DOWNLOAD_FILE_PATH);
    }
}
