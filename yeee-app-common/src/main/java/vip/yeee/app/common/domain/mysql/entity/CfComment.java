package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_cf_comment")
public class CfComment {
    /**
     * 评论id主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目id
     */
    private Integer project;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户id
     */
    private String username;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date time;
}