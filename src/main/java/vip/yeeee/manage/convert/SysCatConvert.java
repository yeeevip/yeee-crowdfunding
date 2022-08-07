package vip.yeeee.manage.convert;

import vip.yeeee.manage.domain.mysql.entity.SysCat;
import vip.yeeee.manage.model.vo.SysCatVO;
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
