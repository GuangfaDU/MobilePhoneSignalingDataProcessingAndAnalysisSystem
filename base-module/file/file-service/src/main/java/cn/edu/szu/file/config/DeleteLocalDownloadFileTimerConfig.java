package cn.edu.szu.file.config;

import cn.edu.szu.file.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class DeleteLocalDownloadFileTimerConfig {

    private static final Logger logger = LoggerFactory.getLogger(DeleteLocalDownloadFileTimerConfig.class);

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteLocalDownloadFile() {
        File directory = new File(IFileService.LOCAL_DOWNLOAD_FILE_PATH);
        if (!directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            boolean res = file.delete();
            if (!res) {
                logger.warn("delete " + file.getName() + " failed");
            }
        }
    }
}
