package cn.edu.szu.algo.component.pretreatment;

import cn.edu.szu.algo.interfaces.DefaultParams;
import cn.edu.szu.algo.client.FileServiceClient;
import cn.edu.szu.algo.interfaces.pretreatment.IRemoveIllegal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class RemoveIllegalImpl extends BaseUtilImpl implements DefaultParams, IRemoveIllegal {

    @Autowired
    public RemoveIllegalImpl(FileServiceClient fileServiceClient, ObjectMapper objectMapper) {
        super(fileServiceClient, objectMapper);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeIllegal() {
        HashSet<String> fileNameSet = getFileList();
        fileNameSet.forEach(fileName -> {
            String[] info = fileName.split("\\.");
            if (!info[0].contains("-pretreated") && !fileNameSet.contains(info[0] + "-pretreated." + info[1])) {
                removeIllegal(fileName);
            }
        });
    }

    private void removeIllegal(String fileName) {
        ensureFileExists(fileName);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        String[] info = fileName.split("\\.");
        String saveFileName = info[0] + "-pretreated." + info[1];
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(DEFAULT_DOWNLOAD_DIRECTORY + fileName)));
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(DEFAULT_UPLOAD_DIRECTORY + saveFileName)));

            String line;
            while ((line = reader.readLine()) != null) {
                if (checkLine(line)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        uploadFile(saveFileName);
    }

    private boolean checkLine(String line) {
        String[] items = line.split(",");
        if (items.length != DEFAULT_DATA_LENGTH) {
            return false;
        }

        if (items[0].length() != DEFAULT_ID_LENGTH) {
            return false;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            simpleDateFormat.parse(items[1]);
            simpleDateFormat.parse(items[2]);
        } catch (ParseException e) {
            return false;
        }

        try {
            Float.parseFloat(items[3]);
            Float.parseFloat(items[4]);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
