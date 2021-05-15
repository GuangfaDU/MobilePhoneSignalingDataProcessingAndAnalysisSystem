package cn.edu.szu.file.util;

import cn.edu.szu.file.entity.CustomFile;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class HdfsTemplate {

    @Autowired
    private FileSystem fileSystem;

    @Value("/user/hdfs/utvs/")
    private String namespace;

    @PostConstruct
    public void init() {
        existDir(namespace, true);
    }

    public String getNamespace() {
        return namespace;
    }

    public boolean existDir(String filePath, boolean createIfNotEx) {
        if (StringUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("filePath不能为空");
        }

        Path path = new Path(filePath);
        try {
            if (!fileSystem.exists(path) && createIfNotEx) {
                fileSystem.mkdirs(path);
            }

            return fileSystem.getFileStatus(path).isDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean rm(String fileName) {
        Path path = new Path(namespace + fileName);
        try {
            fileSystem.delete(path, true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<CustomFile> listFiles(String srcPath) {
        try {
            List<CustomFile> fileList = new LinkedList<>();
            RemoteIterator<LocatedFileStatus> fileIterator = fileSystem.listFiles(new Path(srcPath), false);
            while (fileIterator.hasNext()) {
                fileList.add(parseToCustom(fileIterator.next()));
            }
            return fileList;
        } catch (IOException e) {
            e.printStackTrace();
        }

       return new LinkedList<>();
    }

    public void copyFileToHdfs(String srcFilePath, boolean overwrite, boolean delSrcFile) {
        Path srcPath = new Path(srcFilePath);
        Path destPath = new Path(namespace + srcPath.getName());
        try {
            fileSystem.copyFromLocalFile(delSrcFile, overwrite, srcPath, destPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFromHdfs(String fileName, String destPath) {
        Path hdfsPath = new Path(namespace + fileName);
        try {
            fileSystem.copyToLocalFile(hdfsPath, new Path(destPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CustomFile parseToCustom(LocatedFileStatus fileStatus) {
        CustomFile customFile = new CustomFile();
        Path path = fileStatus.getPath();
        customFile.setFileName(path.getName());
        customFile.setFilePath(path.getParent().toString());
        customFile.setAccessTime(new Timestamp(fileStatus.getAccessTime()));
        customFile.setModificationTime(new Timestamp(fileStatus.getModificationTime()));
        customFile.setContentLength(fileStatus.getLen());
        customFile.setType(fileStatus.isDirectory() ? "directory" : "file");
        return customFile;
    }
}
