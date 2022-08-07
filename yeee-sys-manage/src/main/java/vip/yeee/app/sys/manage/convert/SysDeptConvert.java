package vip.yeee.app.sys.manage.convert;

import vip.yeee.app.sys.manage.model.vo.SysDeptVO;
import vip.yeee.app.common.domain.mysql.entity.SysDept;
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
