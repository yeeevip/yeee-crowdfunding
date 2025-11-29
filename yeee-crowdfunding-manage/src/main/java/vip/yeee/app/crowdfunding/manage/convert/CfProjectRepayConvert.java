package vip.yeee.app.crowdfunding.manage.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfProjectRepay;
import vip.yeee.app.crowdfunding.manage.model.vo.ProjectRepayVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/30 20:18
 */
@Component
public class CfProjectRepayConvert {

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

}
