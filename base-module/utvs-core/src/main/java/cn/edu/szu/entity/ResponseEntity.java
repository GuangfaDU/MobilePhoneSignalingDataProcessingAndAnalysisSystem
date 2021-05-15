package cn.edu.szu.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author GuangfaDu
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class ResponseEntity implements Serializable {
    /**
     * 操作状态
     */
    private boolean success;
    /**
     * 提示和需要补充的信息
     */
    private String message;
    /**
     * 操作返回的数据
     */
    private Object data;
}
