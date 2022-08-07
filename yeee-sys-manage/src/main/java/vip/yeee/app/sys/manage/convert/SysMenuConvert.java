package vip.yeee.app.sys.manage.convert;

import vip.yeee.app.sys.manage.model.vo.SysMenuVO;
import vip.yeee.app.common.domain.mysql.entity.SysMenu;
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
