package vip.yeee.app.crowdfunding.manage.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfOrder;
import vip.yeee.app.crowdfunding.manage.model.vo.SellerOrderVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/29 22:25
 */
@Component
public class CfOrderConvert {

    public SellerOrderVO order2SellerVO(CfOrder order) {
        if (order == null) {
            return null;
        }
        SellerOrderVO vo = new SellerOrderVO();
        vo.setId(order.getId());
        vo.setCode(order.getCode());
        vo.setUserId(order.getUserId());
        vo.setProjectId(order.getProjectId());
        vo.setReceiveInformation(order.getReceiveInformation());
        vo.setProjectRepayId(order.getProjectRepayId());
        vo.setCount(order.getCount());
        vo.setHasPay(order.getHasPay());
        vo.setHasSend(order.getHasSend());
        vo.setOrderDate(order.getOrderDate());
        if (order.getPayPrice() != null) {
            vo.setPayPrice(order.getPayPrice().floatValue());
        }
        return vo;
    }

}
