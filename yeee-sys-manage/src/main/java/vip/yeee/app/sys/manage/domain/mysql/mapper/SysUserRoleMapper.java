package vip.yeee.app.sys.manage.domain.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.yeee.app.common.domain.mysql.entity.SysUserRole;
import vip.yeee.app.sys.manage.model.dto.SysUserRoleDto;

import java.util.List;

/**
 * create by yeah.一页 2022/04/29 14:13:33
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
       List<SysUserRoleDto> getList(SysUserRole sysUserRole);
       SysUserRole getOne(SysUserRole sysUserRole);
       int insert(SysUserRole sysUserRole);
       int updateByPrimaryKey(SysUserRole sysUserRole);
       int batchInsert(List<SysUserRole> sysUserRoleList);
}




