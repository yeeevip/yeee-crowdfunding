package vip.yeeee.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.yeee.memo.integrate.common.model.vo.PageVO;
import vip.yeeee.manage.domain.mysql.entity.SysDept;
import vip.yeeee.manage.model.vo.SysDeptHasSetVO;
import vip.yeeee.manage.model.vo.SysDeptVO;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author yeeee
 * @since 2022-05-29
 */
public interface SysDeptService extends IService<SysDept> {

    PageVO<SysDeptVO> sysDeptPageList(String query);

    boolean sysDeptExist(String query);

    Void addSysDept(SysDeptVO editVO);

    Void editSysDept(SysDeptVO editVO);

    SysDeptVO sysDeptInfo(SysDeptVO editVO);

    Void delSysDept(SysDeptVO editVO);

    SysDeptHasSetVO sysDeptListAndHasSet(Integer userId);

}
