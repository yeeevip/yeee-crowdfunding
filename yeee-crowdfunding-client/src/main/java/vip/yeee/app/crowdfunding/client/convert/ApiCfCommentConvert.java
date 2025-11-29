package vip.yeee.app.crowdfunding.client.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfComment;
import vip.yeee.app.crowdfunding.client.model.vo.CommentVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/29 22:25
 */
@Component
public class ApiCfCommentConvert {

    public CommentVO comment2VO(CfComment comment) {
        if (comment == null) {
            return null;
        }
        CommentVO vo = new CommentVO();
        vo.setId(comment.getId());
        vo.setProjectId(comment.getProject());
        vo.setContent(comment.getContent());
        vo.setUsername(comment.getUsername());
        vo.setTime(comment.getTime());
        return vo;
    }

    public CfComment vo2Comment(CommentVO commentVO) {
        if (commentVO == null) {
            return null;
        }
        CfComment entity = new CfComment();
        entity.setId(commentVO.getId());
        entity.setProject(commentVO.getProjectId());
        entity.setContent(commentVO.getContent());
        entity.setUsername(commentVO.getUsername());
        entity.setTime(commentVO.getTime());
        return entity;
    }

}
