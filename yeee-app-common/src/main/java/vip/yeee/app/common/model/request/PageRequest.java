package vip.yeee.app.common.model.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/17 18:58
 */
@Data
public class PageRequest {

    @NotNull
    private Integer pageNum;

    @Max(15)
    @NotNull
    private Integer pageSize;
}
