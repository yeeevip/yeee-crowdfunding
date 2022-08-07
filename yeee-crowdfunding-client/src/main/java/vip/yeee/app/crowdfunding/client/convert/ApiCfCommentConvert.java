package vip.yeee.app.crowdfunding.client.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import vip.yeee.app.common.domain.mysql.entity.CfComment;
import vip.yeee.app.crowdfunding.client.model.vo.CommentVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 22:25
 */
@Mapper(componentModel = "spring")
public interface ApiCfCommentConvert {

    CommentVO comment2VO(CfComment comment);

    @Mappings(@Mapping(source = "projectId", target = "project"))
    CfComment vo2Comment(CommentVO commentVO);

}
