package vip.yeee.app.crowdfunding.manage.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfProjectRepay;
import vip.yeee.app.crowdfunding.manage.model.vo.ProjectRepayVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 20:18
 */
@Mapper(componentModel = "spring")
public interface CfProjectRepayConvert {

    ProjectRepayVO projectRepay2VO(CfProjectRepay projectRepay);

}
