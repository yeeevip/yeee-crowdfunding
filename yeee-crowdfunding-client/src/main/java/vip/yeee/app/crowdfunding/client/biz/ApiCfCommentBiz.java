package vip.yeee.app.crowdfunding.client.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfComment;
import vip.yeee.app.crowdfunding.client.convert.ApiCfCommentConvert;
import vip.yeee.app.crowdfunding.client.model.dto.CommentDto;
import vip.yeee.app.crowdfunding.client.model.vo.CommentPageReqVO;
import vip.yeee.app.crowdfunding.client.model.vo.CommentVO;
import vip.yeee.app.crowdfunding.client.service.ApiCfCommentService;
import vip.yeee.app.crowdfunding.client.utils.BusinessUtils;
import vip.yeee.memo.base.model.vo.PageVO;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/28 19:47
 */
@Component
public class ApiCfCommentBiz {

    @Resource
    private ApiCfCommentConvert apiCfCommentConvert;
    @Resource
    private ApiCfCommentService apiCfCommentService;

    public Void frontAddComment(CommentVO commentVO) {

        CfComment comment = apiCfCommentConvert.vo2Comment(commentVO);
        comment.setUserId(BusinessUtils.getCurUserId());
        comment.setUsername(BusinessUtils.geCurtUsername());
        comment.setTime(new Date());

        apiCfCommentService.save(comment);

        return null;
    }

    public PageVO<CommentVO> frontCommentPageList(CommentPageReqVO pageReqVO) {
        Page<CfComment> page = PageHelper.startPage(pageReqVO.getPageNum(), pageReqVO.getPageSize());
        List<CommentVO> commentVOList = apiCfCommentService.getCommentListByProjectId(pageReqVO.getCommentVO().getProjectId())
                .stream()
                .map(apiCfCommentConvert::comment2VO)
                .collect(Collectors.toList());
        return new PageVO<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), commentVOList);
    }

    public PageVO<CommentVO> frontReceiveCommentPageList(CommentPageReqVO pageReqVO) {
        Page<CfComment> page = PageHelper.startPage(pageReqVO.getPageNum(), pageReqVO.getPageSize());
        CommentDto query = new CommentDto();
        query.setProjectUserId(BusinessUtils.getCurUserId());
        List<CommentVO> commentVOList = apiCfCommentService.getProjectCommentList(query);
        return new PageVO<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), commentVOList);
    }

    public PageVO<CommentVO> frontSendCommentPageList(CommentPageReqVO pageReqVO) {
        Page<CfComment> page = PageHelper.startPage(pageReqVO.getPageNum(), pageReqVO.getPageSize());
        CommentDto query = new CommentDto();
        query.setUserId(BusinessUtils.getCurUserId());
        List<CommentVO> commentVOList = apiCfCommentService.getProjectCommentList(query);
        return new PageVO<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), commentVOList);
    }
}
