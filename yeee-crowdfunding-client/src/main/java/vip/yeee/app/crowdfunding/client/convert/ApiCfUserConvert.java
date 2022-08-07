package vip.yeee.app.crowdfunding.client.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfUser;
import vip.yeee.app.crowdfunding.client.model.vo.CfUserInfoVO;
import vip.yeee.app.crowdfunding.client.model.vo.UserVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 20:18
 */
@Mapper(componentModel = "spring")
public interface ApiCfUserConvert {

    UserVO user2VO(CfUser user);

    CfUserInfoVO user2InfoVO(CfUser user);

    CfUser vo2Entity(UserVO userVO);

}
