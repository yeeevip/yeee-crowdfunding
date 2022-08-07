package vip.yeee.app.crowdfunding.client.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 19:55
 */
@Data
@Accessors(chain = true)
public class UserBasicVO {

    private Integer id;

    private String username = "";

    private String nickName = "";

}
