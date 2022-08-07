package vip.yeeee.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.yeee.memo.integrate.common.model.vo.PageVO;
import vip.yeeee.manage.domain.mysql.entity.SysRole;
import vip.yeeee.manage.model.vo.SysRoleHasSetVO;
import vip.yeeee.manage.model.vo.SysRoleVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/28 18:09
 */
public interface SysRoleService extends IService<SysRole> {

    PageVO<SysRoleVO> sysRolePageList(String query);

    boolean sysRoleExist(String query);

    Void addSysRole(SysRoleVO editVO);

    Void editSysRole(SysRoleVO editVO);

    SysRoleVO sysRoleInfo(SysRoleVO editVO);

    Void delSysRole(SysRoleVO editVO);

    SysRoleHasSetVO sysRoleListAndHasSet(Integer userId);

}
