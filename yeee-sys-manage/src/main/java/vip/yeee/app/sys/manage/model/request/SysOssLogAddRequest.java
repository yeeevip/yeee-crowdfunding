package vip.yeee.app.sys.manage.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
* create by yeee.一页 2023/06/13 10:43:41
*/
@Data
public class SysOssLogAddRequest {

    @ApiModelProperty("源文件名称")
    @NotBlank
    private String sourceName;

    @ApiModelProperty("存储地址")
    @NotBlank
    private String ossUrl;

    @ApiModelProperty("大小")
    @NotNull
    private BigDecimal size;
}
