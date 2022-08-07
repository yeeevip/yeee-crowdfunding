package vip.yeee.app.crowdfunding.client.model.vo;

import lombok.Data;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 12:30
 */
@Data
public class CommentPageReqVO {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private CommentVO commentVO;

}
