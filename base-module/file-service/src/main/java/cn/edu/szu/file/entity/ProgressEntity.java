package cn.edu.szu.file.entity;

import lombok.Data;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Data
public class ProgressEntity {
    /**
     * 已读取的字节数
     */
    private long bytesRead;
    /**
     * 文件总字节数
     */
    private long contentLength;
    /**
     * 目前正读取第几个文件
     */
    private int item;

    @Override
    public String toString() {
        double curProgress = ((double) bytesRead) / contentLength * 100;
        return "ProgressEntity [bytesRead=" + bytesRead + ", contentLength=" + contentLength
                + ", percentage=" + curProgress + "%, item=" + item;
    }
}
