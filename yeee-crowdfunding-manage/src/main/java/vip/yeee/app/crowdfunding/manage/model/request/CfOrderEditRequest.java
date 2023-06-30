package vip.yeee.app.crowdfunding.manage.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 19:55
 */
@Data
@Accessors(chain = true)
public class CfOrderEditRequest {

    @ApiModelProperty("ID")
    @NotNull(message = "ID不能空", groups = {Edit.class, Info.class})
    private Integer id;

    public interface Base {}
    public interface Add extends Base {}
    public interface Edit extends Base {}
    public interface Info {}
    public interface Del {}

}
