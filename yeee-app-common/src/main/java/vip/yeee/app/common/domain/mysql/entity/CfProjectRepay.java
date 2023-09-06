package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@TableName("t_cf_project_repay")
public class CfProjectRepay {
    /**
     * 回报id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 回报标题
     */
    private String payTitle;

    /**
     * 回报内容
     */
    private String payContent;

    /**
     * 回报类型
     */
    private String type;

    /**
     * 回报时间
     */
    private Integer time;

    /**
     * 回报金额
     */
    private Integer money;
}