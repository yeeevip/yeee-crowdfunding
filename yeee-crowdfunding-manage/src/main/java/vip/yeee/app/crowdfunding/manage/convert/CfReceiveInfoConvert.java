package vip.yeee.app.crowdfunding.manage.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfReceiveInformation;
import vip.yeee.app.crowdfunding.manage.model.vo.ReceiveInfoVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/1 23:18
 */
@Mapper(componentModel = "spring")
public interface CfReceiveInfoConvert {

    ReceiveInfoVO entity2VO(CfReceiveInformation receiveInformation);

}
