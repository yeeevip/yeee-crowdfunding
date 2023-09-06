package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("t_cf_receive_information")
public class CfReceiveInformation {
    /**
     * 收货地址id主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 默认
     */
    private Integer setDefault;
}