package vip.yeee.app.crowdfunding.manage.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfProjectCategory;
import vip.yeee.app.crowdfunding.manage.model.vo.ProjectCategoryVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/5/1 19:57
 */
@Component
public class CfProjectCategoryConvert {

    public ProjectCategoryVO entity2VO(CfProjectCategory projectCategory) {
        if (projectCategory == null) {
            return null;
        }
        ProjectCategoryVO vo = new ProjectCategoryVO();
        vo.setId(projectCategory.getId());
        vo.setCategoryName(projectCategory.getCategoryName());
        vo.setNote(projectCategory.getNote());
        vo.setCreateDate(projectCategory.getCreateDate());
        vo.setChangeDate(projectCategory.getChangeDate());
        vo.setChangePerson(projectCategory.getChangePerson());
        return vo;
    }

}
