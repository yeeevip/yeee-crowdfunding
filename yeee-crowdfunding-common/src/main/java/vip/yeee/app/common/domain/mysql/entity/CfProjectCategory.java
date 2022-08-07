package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_cf_project_category")
public class CfProjectCategory {

    private Integer id;

    private String categoryName;

    private String note;

    private Date createDate;

    private Date changeDate;

    private String changePerson;
}