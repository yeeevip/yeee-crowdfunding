package vip.yeee.app.crowdfunding.manage.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfOrder;
import vip.yeee.app.crowdfunding.manage.model.vo.SellerOrderVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 22:25
 */
@Mapper(componentModel = "spring")
public interface CfOrderConvert {

    SellerOrderVO order2SellerVO(CfOrder order);

}
