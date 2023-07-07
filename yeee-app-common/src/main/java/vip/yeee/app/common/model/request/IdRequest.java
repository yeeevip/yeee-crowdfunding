package vip.yeee.app.common.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
* create by yeee.一页 2023-06-17 14:06:56
*/
@Data
public class IdRequest {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("IDS")
    private List<Long> ids;

}
