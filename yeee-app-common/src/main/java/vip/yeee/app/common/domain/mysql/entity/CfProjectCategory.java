package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_cf_project_category")
public class CfProjectCategory {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String categoryName;

    private String note;

    private Date createDate;

    private Date changeDate;

    private String changePerson;
}