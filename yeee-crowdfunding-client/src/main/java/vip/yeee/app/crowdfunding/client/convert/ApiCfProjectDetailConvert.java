package vip.yeee.app.crowdfunding.client.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfProjectDetail;
import vip.yeee.app.crowdfunding.client.model.vo.ProjectItemVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 22:25
 */
@Mapper(componentModel = "spring")
public interface ApiCfProjectDetailConvert {

    ProjectItemVO detail2VO(CfProjectDetail detail);

    CfProjectDetail vo2Entity(ProjectItemVO itemVO);

}
