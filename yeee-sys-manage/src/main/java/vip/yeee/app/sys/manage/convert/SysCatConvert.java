package vip.yeee.app.sys.manage.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.sys.manage.model.vo.SysCatVO;
import vip.yeee.app.common.domain.mysql.entity.SysCat;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/5/29 11:36
 */
@Component
public class SysCatConvert {

    public SysCatVO entity2VO(SysCat sysCat) {
        if (sysCat == null) {
            return null;
        }
        SysCatVO vo = new SysCatVO();
        vo.setId(sysCat.getId());
        vo.setCode(sysCat.getCode());
        vo.setName(sysCat.getName());
        vo.setPid(sysCat.getPid());
        vo.setTid(sysCat.getTid());
        vo.setSort(sysCat.getSort());
        vo.setRemark(sysCat.getRemark());
        return vo;
    }

    public SysCat vo2Entity(SysCatVO sysCatVO) {
        if (sysCatVO == null) {
            return null;
        }
        SysCat entity = new SysCat();
        entity.setId(sysCatVO.getId());
        entity.setCode(sysCatVO.getCode());
        entity.setName(sysCatVO.getName());
        entity.setPid(sysCatVO.getPid());
        entity.setTid(sysCatVO.getTid());
        entity.setSort(sysCatVO.getSort());
        entity.setRemark(sysCatVO.getRemark());
        return entity;
    }

}
