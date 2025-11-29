package vip.yeee.app.sys.manage.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.sys.manage.model.vo.SysMenuVO;
import vip.yeee.app.common.domain.mysql.entity.SysMenu;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/30 20:18
 */
@Component
public class SysMenuConvert {

    public SysMenuVO entity2VO(SysMenu sysMenu) {
        if (sysMenu == null) {
            return null;
        }
        SysMenuVO vo = new SysMenuVO();
        vo.setId(sysMenu.getId());
        vo.setPid(sysMenu.getPid());
        vo.setName(sysMenu.getName());
        vo.setUrl(sysMenu.getUrl());
        vo.setPerm(sysMenu.getPerm());
        vo.setIcon(sysMenu.getIcon());
        vo.setSeq(sysMenu.getSeq());
        vo.setRemark(sysMenu.getRemark());
        vo.setType(sysMenu.getType());
        return vo;
    }

    public SysMenu vo2Entity(SysMenuVO sysMenuVO) {
        if (sysMenuVO == null) {
            return null;
        }
        SysMenu entity = new SysMenu();
        entity.setId(sysMenuVO.getId());
        entity.setPid(sysMenuVO.getPid());
        entity.setName(sysMenuVO.getName());
        entity.setUrl(sysMenuVO.getUrl());
        entity.setPerm(sysMenuVO.getPerm());
        entity.setIcon(sysMenuVO.getIcon());
        entity.setSeq(sysMenuVO.getSeq());
        entity.setRemark(sysMenuVO.getRemark());
        entity.setType(sysMenuVO.getType());
        return entity;
    }

}
