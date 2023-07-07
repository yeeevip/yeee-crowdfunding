package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_cf_province_city_district")
public class CfProvinceCityDistrict {

    private Integer id;

    private Integer pid;

    private String name;
}