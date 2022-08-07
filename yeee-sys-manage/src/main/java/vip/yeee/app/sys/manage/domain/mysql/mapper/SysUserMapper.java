package vip.yeee.app.sys.manage.domain.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.yeee.app.common.domain.mysql.entity.SysUser;

import java.util.List;

/**
 * create by yeah.一页 2022/04/29 13:52:01
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
       IPage<SysUser> getList(Page<SysUser> page, SysUser sysUser);
       SysUser getOne(SysUser sysUser);
       //int insert(SysUser sysUser);
       int updateByPrimaryKey(SysUser sysUser);
       int batchInsert(List<SysUser> sysUserList);
}




