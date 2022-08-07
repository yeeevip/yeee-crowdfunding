package vip.yeee.app.crowdfunding.manage.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfProjectCategory;
import vip.yeee.app.crowdfunding.manage.model.vo.ProjectCategoryVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/1 19:57
 */
@Mapper(componentModel = "spring")
public interface CfProjectCategoryConvert {

    ProjectCategoryVO entity2VO(CfProjectCategory projectCategory);

}
