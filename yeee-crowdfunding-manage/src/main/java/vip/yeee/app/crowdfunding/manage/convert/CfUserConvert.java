package vip.yeee.app.crowdfunding.manage.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfUser;
import vip.yeee.app.crowdfunding.manage.model.vo.CfUserInfoVO;
import vip.yeee.app.crowdfunding.manage.model.vo.UserVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 20:18
 */
@Mapper(componentModel = "spring")
public interface CfUserConvert {

    UserVO user2VO(CfUser user);

    CfUserInfoVO user2InfoVO(CfUser user);

}
