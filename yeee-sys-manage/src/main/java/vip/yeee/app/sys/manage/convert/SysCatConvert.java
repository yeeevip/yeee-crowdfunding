package vip.yeee.app.sys.manage.convert;

import vip.yeee.app.sys.manage.model.vo.SysCatVO;
import vip.yeee.app.common.domain.mysql.entity.SysCat;
import org.mapstruct.Mapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/29 11:36
 */
@Mapper(componentModel = "spring")
public interface SysCatConvert {

    SysCatVO entity2VO(SysCat sysCat);

    SysCat vo2Entity(SysCatVO sysCatVO);

}
