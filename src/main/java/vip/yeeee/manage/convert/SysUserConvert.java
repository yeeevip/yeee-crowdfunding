package vip.yeeee.manage.convert;

import vip.yeee.memo.integrate.common.security.model.SecurityUser;
import vip.yeeee.manage.domain.mysql.entity.SysUser;
import vip.yeeee.manage.model.vo.SysUserEditVO;
import vip.yeeee.manage.model.vo.SysUserInfoVO;
import vip.yeeee.manage.model.vo.UserVO;
import org.mapstruct.Mapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 20:18
 */
@Mapper(componentModel = "spring")
public interface SysUserConvert {

    UserVO sysUser2VO(SysUser sysUser);

    UserVO securityUser2VO(SecurityUser securityUser);

    SysUser editVO2Entity(SysUserEditVO editVO);

    SysUserInfoVO entity2InfoVO(SysUser sysUser);

}
