package vip.yeee.app.crowdfunding.client.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfProjectDetail;
import vip.yeee.app.crowdfunding.client.model.vo.ProjectItemVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/29 22:25
 */
@Component
public class ApiCfProjectDetailConvert {

    public ProjectItemVO detail2VO(CfProjectDetail detail) {
        if (detail == null) {
            return null;
        }
        ProjectItemVO vo = new ProjectItemVO();
        vo.setItemTitle(detail.getItemTitle());
        vo.setItemContent(detail.getItemContent());
        return vo;
    }

    public CfProjectDetail vo2Entity(ProjectItemVO itemVO) {
        if (itemVO == null) {
            return null;
        }
        CfProjectDetail entity = new CfProjectDetail();
        entity.setItemTitle(itemVO.getItemTitle());
        entity.setItemContent(itemVO.getItemContent());
        return entity;
    }

}
