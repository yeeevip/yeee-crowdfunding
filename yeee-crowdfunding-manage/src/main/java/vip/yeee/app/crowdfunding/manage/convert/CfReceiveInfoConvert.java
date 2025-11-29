package vip.yeee.app.crowdfunding.manage.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfReceiveInformation;
import vip.yeee.app.crowdfunding.manage.model.vo.ReceiveInfoVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/5/1 23:18
 */
@Component
public class CfReceiveInfoConvert {

    public ReceiveInfoVO entity2VO(CfReceiveInformation receiveInformation) {
        if (receiveInformation == null) {
            return null;
        }
        ReceiveInfoVO vo = new ReceiveInfoVO();
        vo.setId(receiveInformation.getId());
        vo.setReceiver(receiveInformation.getReceiver());
        vo.setPhone(receiveInformation.getPhone());
        vo.setAddress(receiveInformation.getAddress());
        vo.setSetDefault(receiveInformation.getSetDefault());
        return vo;
    }

}
