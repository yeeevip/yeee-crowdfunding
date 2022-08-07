package vip.yeee.app.crowdfunding.client.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/2/6 17:31
 */
@Data
public class UserAccountFillRequest {

    @NotNull
    private Long id;

    @NotNull
    private Long balance;

}
