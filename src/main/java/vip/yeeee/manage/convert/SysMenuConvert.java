package vip.yeeee.manage.convert;

import vip.yeeee.manage.domain.mysql.entity.SysMenu;
import vip.yeeee.manage.model.vo.SysMenuVO;
import org.mapstruct.Mapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 20:18
 */
@Mapper(componentModel = "spring")
public interface SysMenuConvert {

    SysMenuVO entity2VO(SysMenu sysMenu);

    SysMenu vo2Entity(SysMenuVO sysMenuVO);

}
