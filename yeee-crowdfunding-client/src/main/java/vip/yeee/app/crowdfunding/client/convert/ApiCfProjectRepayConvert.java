package vip.yeee.app.crowdfunding.client.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfProjectRepay;
import vip.yeee.app.crowdfunding.client.model.vo.ProjectRepayVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 20:18
 */
@Mapper(componentModel = "spring")
public interface ApiCfProjectRepayConvert {

    ProjectRepayVO projectRepay2VO(CfProjectRepay projectRepay);

    CfProjectRepay vo2Entity( ProjectRepayVO projectRepayVO);

}
