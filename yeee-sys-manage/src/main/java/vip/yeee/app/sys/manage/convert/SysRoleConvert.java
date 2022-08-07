package vip.yeee.app.sys.manage.convert;

import vip.yeee.app.sys.manage.model.vo.SysRoleVO;
import vip.yeee.app.common.domain.mysql.entity.SysRole;
import vip.yeee.app.sys.manage.model.vo.SysRoleHasSetVO;
import org.mapstruct.Mapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/28 18:34
 */
@Mapper(componentModel = "spring")
public interface SysRoleConvert {

    SysRole vo2Entity(SysRoleVO sysRoleVO);

    SysRoleVO entity2VO(SysRole sysRole);

    SysRoleHasSetVO.RoleVO entity2SetVO(SysRole sysRole);

}
