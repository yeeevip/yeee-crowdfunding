package vip.yeee.app.crowdfunding.manage.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfProject;
import vip.yeee.app.crowdfunding.manage.model.vo.LunchProjectVO;
import vip.yeee.app.crowdfunding.manage.model.vo.ProjectVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/29 22:25
 */
@Component
public class CfProjectConvert {

    public ProjectVO project2VO(CfProject project) {
        if (project == null) {
            return null;
        }
        ProjectVO vo = new ProjectVO();
        vo.setId(project.getId());
        vo.setProjectType(project.getCategoryId());
        vo.setTitle(project.getTitle());
        vo.setUserId(project.getUserId());
        vo.setBlurb(project.getBlurb());
        vo.setCoverPath(project.getCoverPath());
        vo.setLaunchDateRaising(project.getLaunchDateRaising());
        vo.setTotalFundRaising(project.getTotalFundRaising());
        vo.setDaysRaising(project.getDaysRaising());
        vo.setHasFundRaising(project.getHasFundRaising());
        vo.setHasAudits(project.getHasAudits());
        vo.setHasFinish(project.getHasFinish());
        vo.setHasDown(project.getHasDown());
        vo.setKeyword(project.getKeyword());
        return vo;
    }

    public LunchProjectVO project2LunchProjectVO(CfProject project) {
        if (project == null) {
            return null;
        }
        LunchProjectVO vo = new LunchProjectVO();
        vo.setProjectType(project.getCategoryId());
        vo.setCoverPath(project.getCoverPath());
        vo.setTitle(project.getTitle());
        vo.setBlurb(project.getBlurb());
        vo.setTotalFundRaising(project.getTotalFundRaising());
        vo.setDaysRaising(project.getDaysRaising());
        vo.setShenfen(project.getShenfen());
        return vo;
    }

}
