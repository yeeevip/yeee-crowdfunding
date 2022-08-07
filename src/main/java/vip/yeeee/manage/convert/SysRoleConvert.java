package vip.yeeee.manage.convert;

import vip.yeeee.manage.domain.mysql.entity.SysRole;
import vip.yeeee.manage.model.vo.SysRoleHasSetVO;
import vip.yeeee.manage.model.vo.SysRoleVO;
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
