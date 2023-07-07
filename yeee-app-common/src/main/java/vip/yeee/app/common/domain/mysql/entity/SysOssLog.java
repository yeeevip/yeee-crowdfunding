package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OSS上传日志
 */
@Data
@TableName("sys_oss_log")
public class SysOssLog {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 源文件名称
     */
    private String sourceName;

    /**
     * 存储地址
     */
    private String ossUrl;

    /**
     * 大小
     */
    private BigDecimal size;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}