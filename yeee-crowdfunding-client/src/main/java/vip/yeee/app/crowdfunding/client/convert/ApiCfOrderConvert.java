package vip.yeee.app.crowdfunding.client.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfOrder;
import vip.yeee.app.crowdfunding.client.model.vo.BuyOrderVO;
import vip.yeee.app.crowdfunding.client.model.vo.SellerOrderVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 22:25
 */
@Mapper(componentModel = "spring")
public interface ApiCfOrderConvert {

    BuyOrderVO order2VO(CfOrder order);
    SellerOrderVO order2SellerVO(CfOrder order);

}
