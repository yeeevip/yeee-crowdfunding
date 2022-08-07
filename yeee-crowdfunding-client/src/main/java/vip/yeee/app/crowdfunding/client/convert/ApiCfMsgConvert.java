package vip.yeee.app.crowdfunding.client.convert;

import org.springframework.beans.BeanUtils;
import vip.yeee.app.common.domain.mysql.entity.CfMsg;
import vip.yeee.app.crowdfunding.client.model.vo.MsgListVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/2/6 18:37
 */
public class ApiCfMsgConvert {
    public static MsgListVO po2ListVo(CfMsg tMsg) {
        MsgListVO vo = new MsgListVO();
        if (tMsg != null) {
            BeanUtils.copyProperties(tMsg, vo);
            vo.setId(tMsg.getId().toString());
        }
        return vo;
    }
}
