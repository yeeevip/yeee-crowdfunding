package vip.yeee.app.sys.manage.domain.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.yeee.app.common.domain.mysql.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * create by yeah.一页 2022/05/04 17:02:07
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
       List<SysMenu> getList(SysMenu sysMenu);
       SysMenu getOne(SysMenu sysMenu);
       //int insert(SysMenu sysMenu);
       int updateByPrimaryKey(SysMenu sysMenu);
       int batchInsert(List<SysMenu> sysMenuList);

       List<SysMenu> getListByRoleIds(@Param("list") List<Integer> list, @Param("type") Integer type);
}




