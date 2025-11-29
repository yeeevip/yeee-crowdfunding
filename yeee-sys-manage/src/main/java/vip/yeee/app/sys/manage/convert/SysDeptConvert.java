package vip.yeee.app.sys.manage.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.sys.manage.model.vo.SysDeptVO;
import vip.yeee.app.common.domain.mysql.entity.SysDept;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/5/29 11:36
 */
@Component
public class SysDeptConvert {

    public SysDeptVO entity2VO(SysDept sysDept) {
        if (sysDept == null) {
            return null;
        }
        SysDeptVO vo = new SysDeptVO();
        vo.setId(sysDept.getId());
        vo.setCode(sysDept.getCode());
        vo.setName(sysDept.getName());
        vo.setPid(sysDept.getPid());
        vo.setSort(sysDept.getSort());
        vo.setRemark(sysDept.getRemark());
        return vo;
    }

    public SysDept vo2Entity(SysDeptVO sysDeptVO) {
        if (sysDeptVO == null) {
            return null;
        }
        SysDept entity = new SysDept();
        entity.setId(sysDeptVO.getId());
        entity.setCode(sysDeptVO.getCode());
        entity.setName(sysDeptVO.getName());
        entity.setPid(sysDeptVO.getPid());
        entity.setSort(sysDeptVO.getSort());
        entity.setRemark(sysDeptVO.getRemark());
        return entity;
    }

}
