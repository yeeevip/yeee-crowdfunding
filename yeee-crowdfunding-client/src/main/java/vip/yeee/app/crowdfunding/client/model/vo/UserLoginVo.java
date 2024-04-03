package vip.yeee.app.crowdfunding.client.model.vo;

import lombok.Data;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2023/6/29 22:03
 */
@Data
public class UserLoginVo {
    private String token;
    private String refreshToken;
    private String tokenHead;
    private long expiresIn;
}
