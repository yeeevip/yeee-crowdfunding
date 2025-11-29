package vip.yeee.app.crowdfunding.client.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfReceiveInformation;
import vip.yeee.app.crowdfunding.client.model.vo.ReceiveInfoVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/5/1 23:18
 */
@Component
public class ApiCfReceiveInfoConvert {

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

    public CfReceiveInformation vo2Entity(ReceiveInfoVO receiveInfoVO) {
        if (receiveInfoVO == null) {
            return null;
        }
        CfReceiveInformation entity = new CfReceiveInformation();
        entity.setId(receiveInfoVO.getId());
        entity.setReceiver(receiveInfoVO.getReceiver());
        entity.setPhone(receiveInfoVO.getPhone());
        entity.setAddress(receiveInfoVO.getAddress());
        entity.setSetDefault(receiveInfoVO.getSetDefault());
        return entity;
    }

}
