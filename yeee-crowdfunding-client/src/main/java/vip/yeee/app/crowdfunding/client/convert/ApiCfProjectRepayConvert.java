package vip.yeee.app.crowdfunding.client.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfProjectRepay;
import vip.yeee.app.crowdfunding.client.model.vo.ProjectRepayVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/30 20:18
 */
@Component
public class ApiCfProjectRepayConvert {

    public ProjectRepayVO projectRepay2VO(CfProjectRepay projectRepay) {
        if (projectRepay == null) {
            return null;
        }
        ProjectRepayVO vo = new ProjectRepayVO();
        vo.setId(projectRepay.getId());
        vo.setPayTitle(projectRepay.getPayTitle());
        vo.setPayContent(projectRepay.getPayContent());
        vo.setType(projectRepay.getType());
        vo.setTime(projectRepay.getTime());
        vo.setMoney(projectRepay.getMoney());
        return vo;
    }

    public CfProjectRepay vo2Entity(ProjectRepayVO projectRepayVO) {
        if (projectRepayVO == null) {
            return null;
        }
        CfProjectRepay entity = new CfProjectRepay();
        entity.setId(projectRepayVO.getId());
        entity.setPayTitle(projectRepayVO.getPayTitle());
        entity.setPayContent(projectRepayVO.getPayContent());
        entity.setType(projectRepayVO.getType());
        entity.setTime(projectRepayVO.getTime());
        entity.setMoney(projectRepayVO.getMoney());
        return entity;
    }

}
