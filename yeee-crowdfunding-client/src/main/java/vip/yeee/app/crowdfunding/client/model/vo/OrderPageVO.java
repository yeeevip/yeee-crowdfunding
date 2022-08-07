package vip.yeee.app.crowdfunding.client.model.vo;

import lombok.Data;

import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 21:42
 */
@Data
public class OrderPageVO {

    private List<ProjectRepayVO> repayVOList;

    private ReceiveInfoVO receiveInfoVO;

}
