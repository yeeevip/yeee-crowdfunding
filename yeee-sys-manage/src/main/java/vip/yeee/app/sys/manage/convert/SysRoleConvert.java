package vip.yeee.app.sys.manage.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.sys.manage.model.vo.SysRoleVO;
import vip.yeee.app.common.domain.mysql.entity.SysRole;
import vip.yeee.app.sys.manage.model.vo.SysRoleHasSetVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/5/28 18:34
 */
@Component
public class SysRoleConvert {

    public SysRole vo2Entity(SysRoleVO sysRoleVO) {
        if (sysRoleVO == null) {
            return null;
        }
        SysRole entity = new SysRole();
        entity.setId(sysRoleVO.getId());
        entity.setCode(sysRoleVO.getCode());
        entity.setName(sysRoleVO.getName());
        entity.setRemark(sysRoleVO.getRemark());
        return entity;
    }

    public SysRoleVO entity2VO(SysRole sysRole) {
        if (sysRole == null) {
            return null;
        }
        SysRoleVO vo = new SysRoleVO();
        vo.setId(sysRole.getId());
        vo.setCode(sysRole.getCode());
        vo.setName(sysRole.getName());
        vo.setRemark(sysRole.getRemark());
        return vo;
    }

    public SysRoleHasSetVO.RoleVO entity2SetVO(SysRole sysRole) {
        if (sysRole == null) {
            return null;
        }
        SysRoleHasSetVO.RoleVO vo = new SysRoleHasSetVO.RoleVO();
        vo.setId(sysRole.getId());
        vo.setName(sysRole.getName());
        return vo;
    }

}
