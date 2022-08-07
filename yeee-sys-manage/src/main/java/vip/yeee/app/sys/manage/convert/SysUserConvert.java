package vip.yeee.app.sys.manage.convert;

import vip.yeee.app.sys.manage.model.vo.SysUserEditVO;
import vip.yeee.app.sys.manage.model.vo.UserVO;
import vip.yeee.app.sys.manage.model.vo.SysUserInfoVO;
import vip.yeee.app.common.domain.mysql.entity.SysUser;
import org.mapstruct.Mapper;
import vip.yeee.memo.base.websecurityoauth2.model.AuthedUser;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 20:18
 */
@Mapper(componentModel = "spring")
public interface SysUserConvert {

    UserVO sysUser2VO(SysUser sysUser);

    UserVO securityUser2VO(AuthedUser securityUser);

    SysUser editVO2Entity(SysUserEditVO editVO);

    SysUserInfoVO entity2InfoVO(SysUser sysUser);

}
