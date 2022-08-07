package vip.yeee.app.crowdfunding.client.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.yeee.app.crowdfunding.client.biz.ApiCfCommentBiz;
import vip.yeee.app.crowdfunding.client.model.vo.CommentPageReqVO;
import vip.yeee.app.crowdfunding.client.model.vo.CommentVO;
import vip.yeee.memo.base.model.annotation.AnonymousAccess;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/1 10:14
 */
@RequiredArgsConstructor
@Api(tags = "众筹前台-评论接口")
@RequestMapping("/api/cf/comment")
@RestController
public class ApiCfCommentController {

    private final ApiCfCommentBiz apiCfCommentBiz;

    @ApiOperation("新增评论")
    @PostMapping("/add")
    public CommonResult<Void> frontAddComment(@Validated @RequestBody CommentVO commentVO) {
        return CommonResult.success(apiCfCommentBiz.frontAddComment(commentVO));
    }

    @ApiOperation("评论列表")
    @AnonymousAccess
    @PostMapping("/list")
    public CommonResult<PageVO<CommentVO>> frontCommentPageList(@Validated(CommentVO.PageListGroup.class) @RequestBody CommentPageReqVO pageReqVO) {
        return CommonResult.success(apiCfCommentBiz.frontCommentPageList(pageReqVO));
    }

    @ApiOperation("评论列表")
    @PostMapping("/receive")
    public CommonResult<PageVO<CommentVO>> frontReceiveCommentPageList(@RequestBody CommentPageReqVO pageReqVO) {
        return CommonResult.success(apiCfCommentBiz.frontReceiveCommentPageList(pageReqVO));
    }

    @ApiOperation("评论列表")
    @PostMapping("/send")
    public CommonResult<PageVO<CommentVO>> frontSendCommentPageList(@RequestBody CommentPageReqVO pageReqVO) {
        return CommonResult.success(apiCfCommentBiz.frontSendCommentPageList(pageReqVO));
    }

}
