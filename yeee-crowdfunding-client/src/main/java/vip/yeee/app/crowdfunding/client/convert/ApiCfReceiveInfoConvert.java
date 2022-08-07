package vip.yeee.app.crowdfunding.client.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfReceiveInformation;
import vip.yeee.app.crowdfunding.client.model.vo.ReceiveInfoVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/1 23:18
 */
@Mapper(componentModel = "spring")
public interface ApiCfReceiveInfoConvert {

    ReceiveInfoVO entity2VO(CfReceiveInformation receiveInformation);

    CfReceiveInformation vo2Entity(ReceiveInfoVO receiveInfoVO);

}
