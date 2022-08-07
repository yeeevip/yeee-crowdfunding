package vip.yeeee.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.yeee.memo.integrate.common.model.vo.PageVO;
import vip.yeeee.manage.domain.mysql.entity.SysCat;
import vip.yeeee.manage.model.vo.SysCatVO;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author yeeee
 * @since 2022-05-29
 */
public interface SysCatService extends IService<SysCat> {

    PageVO<SysCatVO> sysCatPageList(String query);

    PageVO<SysCatVO> sysItemTreeList(String query);

    boolean sysCatExist(String query);

    Void addSysCat(SysCatVO editVO);

    Void editSysCat(SysCatVO editVO);

    SysCatVO sysCatInfo(SysCatVO editVO);

    Void delSysCat(SysCatVO editVO);

}
