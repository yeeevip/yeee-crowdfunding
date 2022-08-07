package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yeeee
 * @since 2023-02-06
 */
@Data
@Accessors(chain = true)
@TableName("t_cf_msg")
public class CfMsg {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 1：项目
     */
    private Integer subjectType;

    private String subjectId;

    private String content;

    private String target;

    private Integer hasRead;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;


}
