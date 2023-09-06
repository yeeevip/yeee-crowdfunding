package vip.yeee.app.crowdfunding.client.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.yeee.app.common.model.request.PageRequest;
import vip.yeee.app.crowdfunding.client.biz.ApiCfMsgBiz;
import vip.yeee.app.crowdfunding.client.model.vo.IdsRequest;
import vip.yeee.app.crowdfunding.client.model.vo.MsgListVO;
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
@Api(tags = "众筹前台-消息接口")
@RequestMapping("/api/cf/msg")
@RestController
public class ApiCfMsgController {

    private final ApiCfMsgBiz apiCfMsgBiz;

    @ApiOperation("消息列表")
    @PostMapping("/list")
    public CommonResult<PageVO<MsgListVO>> frontMsgPageList(@RequestBody PageRequest request) {
        return CommonResult.success(apiCfMsgBiz.frontMsgPageList(request));
    }

    @ApiOperation("读取消息")
    @PostMapping("/read")
    public CommonResult<Void> frontReadMsgList(@RequestBody IdsRequest request) {
        return CommonResult.success(apiCfMsgBiz.frontReadMsgList(request));
    }

}
