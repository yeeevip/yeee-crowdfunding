package vip.yeee.app.crowdfunding.manage.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfProject;
import vip.yeee.app.crowdfunding.manage.model.vo.LunchProjectVO;
import vip.yeee.app.crowdfunding.manage.model.vo.ProjectVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 22:25
 */
@Mapper(componentModel = "spring")
public interface CfProjectConvert {

    ProjectVO project2VO(CfProject project);

    LunchProjectVO project2LunchProjectVO(CfProject project);

}
