package vip.yeee.app.crowdfunding.client.domain.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.yeee.app.common.domain.mysql.entity.CfComment;
import vip.yeee.app.crowdfunding.client.model.dto.CommentDto;
import vip.yeee.app.crowdfunding.client.model.vo.CommentVO;

import java.util.List;

/**
 * create by yeah.一页 2022/04/30 22:24:49
 */
public interface ApiCfCommentMapper extends BaseMapper<CfComment> {

    List<CommentVO> getProjectCommentList(CommentDto query);
}




