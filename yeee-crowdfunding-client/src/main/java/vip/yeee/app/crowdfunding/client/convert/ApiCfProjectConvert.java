package vip.yeee.app.crowdfunding.client.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import vip.yeee.app.common.domain.mysql.entity.CfProject;
import vip.yeee.app.crowdfunding.client.model.vo.LunchProjectVO;
import vip.yeee.app.crowdfunding.client.model.vo.ProjectDetailVO;
import vip.yeee.app.crowdfunding.client.model.vo.ProjectVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 22:25
 */
@Mapper(componentModel = "spring")
public interface ApiCfProjectConvert {

    ProjectVO project2VO(CfProject project);

    ProjectDetailVO project2DetailVO(CfProject project);

    @Mappings(@Mapping(source = "projectType", target = "categoryId"))
    CfProject lunchProjectVOProject(LunchProjectVO lunchProjectVO);

    LunchProjectVO project2LunchProjectVO(CfProject project);

}
