package vip.yeee.app.crowdfunding.client.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfProjectProgress;
import vip.yeee.app.crowdfunding.client.model.vo.ProjectProgressVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 22:25
 */
@Mapper(componentModel = "spring")
public interface ApiCfProjectProgressConvert {

    ProjectProgressVO progress2VO(CfProjectProgress projectProgress);

}
