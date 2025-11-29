package vip.yeee.app.crowdfunding.client.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfProject;
import vip.yeee.app.crowdfunding.client.model.vo.LunchProjectVO;
import vip.yeee.app.crowdfunding.client.model.vo.ProjectDetailVO;
import vip.yeee.app.crowdfunding.client.model.vo.ProjectVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/29 22:25
 */
@Component
public class ApiCfProjectConvert {

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

    public ProjectDetailVO project2DetailVO(CfProject project) {
        if (project == null) {
            return null;
        }
        ProjectDetailVO vo = new ProjectDetailVO();
        vo.setId(project.getId());
        vo.setProjectType(project.getCategoryId());
        vo.setTitle(project.getTitle());
        vo.setBlurb(project.getBlurb());
        vo.setCoverPath(project.getCoverPath());
        vo.setLaunchDateRaising(project.getLaunchDateRaising());
        vo.setTotalFundRaising(project.getTotalFundRaising());
        vo.setHasFundRaising(project.getHasFundRaising());
        vo.setHasAudits(project.getHasAudits());
        vo.setHasFinish(project.getHasFinish());
        return vo;
    }

    public CfProject lunchProjectVOProject(LunchProjectVO lunchProjectVO) {
        if (lunchProjectVO == null) {
            return null;
        }
        CfProject entity = new CfProject();
        entity.setCategoryId(lunchProjectVO.getProjectType());
        entity.setCoverPath(lunchProjectVO.getCoverPath());
        entity.setTitle(lunchProjectVO.getTitle());
        entity.setBlurb(lunchProjectVO.getBlurb());
        entity.setTotalFundRaising(lunchProjectVO.getTotalFundRaising());
        entity.setDaysRaising(lunchProjectVO.getDaysRaising());
        entity.setShenfen(lunchProjectVO.getShenfen());
        return entity;
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
