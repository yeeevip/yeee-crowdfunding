package vip.yeee.app.sys.manage.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
* create by yeee.一页 2023/06/13 10:43:41
*/
@Data
public class SysOssLogListVo {

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("源文件名称")
    private String sourceName;

    @ApiModelProperty("存储地址")
    private String ossUrl;

    @ApiModelProperty("大小")
    private BigDecimal size;

    @ApiModelProperty("")
    private String createBy;

    @ApiModelProperty("")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("")
    private String updateBy;

    @ApiModelProperty("")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
