package vip.yeee.app.crowdfunding.client.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/2/6 18:29
 */
@Data
public class MsgListVO {

    private String id;

    private String content;

    private LocalDateTime createTime;

}
