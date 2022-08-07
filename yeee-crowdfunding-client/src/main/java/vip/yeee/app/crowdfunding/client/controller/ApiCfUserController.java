package vip.yeee.app.crowdfunding.client.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.yeee.app.crowdfunding.client.biz.ApiCfUserBiz;
import vip.yeee.app.crowdfunding.client.model.vo.UpdatePasswordVO;
import vip.yeee.app.crowdfunding.client.model.vo.UserCheckVO;
import vip.yeee.app.crowdfunding.client.model.vo.UserLoginVo;
import vip.yeee.app.crowdfunding.client.model.vo.UserVO;
import vip.yeee.memo.base.model.annotation.AnonymousAccess;
import vip.yeee.memo.base.model.rest.CommonResult;

import javax.validation.Valid;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/28 22:53
 */
@Slf4j
@Api(tags = "众筹前台-用户管理")
@RequiredArgsConstructor
@RequestMapping("/api/cf/user")
@RestController
public class ApiCfUserController {

    private final ApiCfUserBiz apiCfUserBiz;

    @AnonymousAccess
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名", name = "username"),
            @ApiImplicitParam(value = "密码", name = "password")
    })
    @PostMapping(value = "/login")
    public CommonResult<UserLoginVo> login(@Valid UserCheckVO userCheckVO) {
        return CommonResult.success(apiCfUserBiz.login(userCheckVO));
    }

    @ApiOperation("退出登录")
    @GetMapping(value = "/logout")
    public CommonResult<Void> logout() {
        return CommonResult.success(apiCfUserBiz.logout());
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePassword")
    public CommonResult<Void> updatePassword(@Validated @RequestBody UpdatePasswordVO updatePasswordVO) {
        return CommonResult.success(apiCfUserBiz.updatePassword(updatePasswordVO));
    }

    @AnonymousAccess
    @ApiOperation("用户注册")
    @PostMapping(value = "/register")
    public CommonResult<Void> register(@Validated UserCheckVO userCheckVO) {
        return CommonResult.success(apiCfUserBiz.register(userCheckVO));
    }

    @ApiOperation("修改资料")
    @PostMapping("/update")
    public CommonResult<Void> updateMyselfInfo(@RequestBody UserVO userVO) {
        return CommonResult.success(apiCfUserBiz.updateMyselfInfo(userVO));
    }

    @ApiOperation("个人资料")
    @GetMapping("/info")
    public CommonResult<UserVO> getMyselfInfo() {
        return CommonResult.success(apiCfUserBiz.getMyselfInfo());
    }

}
