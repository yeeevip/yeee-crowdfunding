package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_cf_order")
public class CfOrder {
    /**
     * 订单主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    private String code;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 下单用户
     */
    private Integer userId;

    /**
     * 回报id
     */
    private Integer projectRepayId;

    /**
     * 回报份数
     */
    private Integer count;

    /**
     * 是否支付
     */
    private Integer hasPay;

    /**
     * 是否发货
     */
    private Integer hasSend;

    /**
     * 创建时间
     */
    private Date orderDate;

    private Integer hasReceive;

    private Integer receiveInformation;

    /**
     * 卖家id
     */
    private Integer userSeller;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付金额
     */
    private BigDecimal payPrice;

    /**
     * 订单有效标志  1:有效  0：无效
     */
    private Integer status;
}