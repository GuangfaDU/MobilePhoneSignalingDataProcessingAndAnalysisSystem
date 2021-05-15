package cn.edu.szu.file.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
public class CustomFile {

    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 创建时间
     */
    private Date accessTime;
    /**
     * 修改时间
     */
    private Date modificationTime;
    /**
     * 文件大小
     */
    private Long contentLength;
}
