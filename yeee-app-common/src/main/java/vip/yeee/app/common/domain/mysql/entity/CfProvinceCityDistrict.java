package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_cf_province_city_district")
public class CfProvinceCityDistrict {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    private String name;
}