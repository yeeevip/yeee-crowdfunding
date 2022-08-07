package vip.yeee.app.sys.manage.model.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/28 18:23
 */
@Data
public class SysNewsVO {

    @NotNull(message = "ID不能为空", groups = {Edit.class, Info.class})
    private Integer id;

    @NotBlank(message = "name不能为空", groups = {Base.class})
    private String name;

    private String content;

    private String author;

    private String imgUrl;

    private String remark;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime recordTime;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private LocalDateTime releaseTime;

    private Integer readQty;

    @NotNull(message = "IDS不能为空", groups = {Del.class})
    @Size(min = 1, message = "IDS不能为空", groups = {Del.class})
    private List<Integer> ids;

    public interface Base {}
    public interface Add extends Base {}
    public interface Edit extends Base {}
    public interface Info {}
    public interface Del {}

}
