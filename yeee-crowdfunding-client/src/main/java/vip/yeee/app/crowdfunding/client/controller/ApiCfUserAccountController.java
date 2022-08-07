package vip.yeee.app.crowdfunding.client.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import vip.yeee.app.crowdfunding.client.biz.ApiCfUserAccountBiz;
import vip.yeee.app.crowdfunding.client.model.vo.UserAccountFillRequest;
import vip.yeee.app.crowdfunding.client.model.vo.UserAccountVO;
import vip.yeee.memo.base.model.rest.CommonResult;

import javax.annotation.Resource;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/1 10:14
 */
@Api(tags = "众筹前台-用户钱包接口")
@RequestMapping("/api/cf/userAccount")
@RestController
public class ApiCfUserAccountController {

    @Resource
    private ApiCfUserAccountBiz apiCfUserAccountBiz;

    @ApiOperation("我的钱包")
    @GetMapping("/my")
    public CommonResult<UserAccountVO> frontMyAccount() {
        return CommonResult.success(apiCfUserAccountBiz.frontMyAccount());
    }

    @ApiOperation("钱包充值")
    @PostMapping("/fill")
    public CommonResult<Void> frontFillAccount(@RequestBody UserAccountFillRequest request) {
        return CommonResult.success(apiCfUserAccountBiz.frontFillAccount(request));
    }

}
