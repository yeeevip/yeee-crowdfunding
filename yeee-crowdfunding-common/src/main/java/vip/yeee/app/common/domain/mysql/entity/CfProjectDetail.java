package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("t_cf_project_detail")
public class CfProjectDetail {

    private Integer id;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 标题
     */
    private String itemTitle;

    /**
     * 内容
     */
    private String itemContent;
}