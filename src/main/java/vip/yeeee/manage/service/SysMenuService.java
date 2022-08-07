package vip.yeeee.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.yeee.memo.integrate.common.model.vo.PageVO;
import vip.yeeee.manage.domain.mysql.entity.SysMenu;
import vip.yeeee.manage.model.vo.SysMenuHasSetVO;
import vip.yeeee.manage.model.vo.SysMenuVO;

import java.util.List;
import java.util.Map;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/4 17:07
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuVO> getMenuListTree();

    PageVO<SysMenuVO> getSysMenuListTreenode(String query);

    Map<String, Object> getMenuAuthz();

    Void addSysMenu(SysMenuVO editVO);

    Void editSysMenu(SysMenuVO editVO);

    SysMenuVO sysMenuInfo(SysMenuVO editVO);

    Void delSysMenu(SysMenuVO editVO);

    SysMenuHasSetVO sysMenuListAndHasSet(Integer roleId);

}
