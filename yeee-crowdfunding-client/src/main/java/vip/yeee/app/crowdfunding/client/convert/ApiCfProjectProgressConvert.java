package vip.yeee.app.crowdfunding.client.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfProjectProgress;
import vip.yeee.app.crowdfunding.client.model.vo.ProjectProgressVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/29 22:25
 */
@Component
public class ApiCfProjectProgressConvert {

    public ProjectProgressVO progress2VO(CfProjectProgress projectProgress) {
        if (projectProgress == null) {
            return null;
        }
        ProjectProgressVO vo = new ProjectProgressVO();
        vo.setContent(projectProgress.getContent());
        vo.setPublishDate(projectProgress.getPublishDate());
        vo.setPubUser(projectProgress.getPubUser());
        vo.setProjectId(projectProgress.getProjectId());
        return vo;
    }

}
