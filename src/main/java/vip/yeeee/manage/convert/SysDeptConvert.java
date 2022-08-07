package vip.yeeee.manage.convert;

import vip.yeeee.manage.domain.mysql.entity.SysDept;
import vip.yeeee.manage.model.vo.SysDeptVO;
import org.mapstruct.Mapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/29 11:36
 */
@Mapper(componentModel = "spring")
public interface SysDeptConvert {

    SysDeptVO entity2VO(SysDept sysDept);

    SysDept vo2Entity(SysDeptVO sysDeptVO);

}
