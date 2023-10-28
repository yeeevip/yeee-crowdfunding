package vip.yeee.app.sys.manage.controller;

import vip.yeee.app.sys.manage.model.vo.*;
import vip.yeee.app.sys.manage.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.yeee.memo.base.model.annotation.AnonymousAccess;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;
import vip.yeee.memo.base.websecurityoauth2.model.Oauth2TokenVo;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/28 22:53
 */
@Slf4j
@Api(tags = "系统后台-系统用户管理")
@RequiredArgsConstructor
@RequestMapping("/manage/sys-user")
@RestController
public class SysUserController {

    private final SysUserService sysUserService;

    @AnonymousAccess
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名", name = "username"),
            @ApiImplicitParam(value = "密码", name = "password")
    })
    @PostMapping(value = "/login")
    public CommonResult<Oauth2TokenVo> login(String username, String password) {
        return CommonResult.success(sysUserService.login(username, password));
    }

    @ApiOperation("退出登录")
    @GetMapping(value = "/logout")
    public CommonResult<Object> logout() {
        return CommonResult.success(sysUserService.userLogout());
    }

    @ApiOperation("用户分页")
    @PostMapping(value = "/page")
    public CommonResult<PageVO<UserVO>> sysUserPageList(SysUserPageReqVO sysUserPageReqVO) {
        return CommonResult.success(sysUserService.sysUserPageList(sysUserPageReqVO));
    }

    @ApiOperation("用户分页")
    @GetMapping(value = "/page")
    public CommonResult<PageVO<UserVO>> sysUserPageList(String query) {
        return CommonResult.success(sysUserService.sysUserPageList(query));
    }

    @ApiOperation("用户是否存在")
    @GetMapping(value = "/exist")
    public CommonResult<Boolean> sysUserExist(String query) {
        return CommonResult.success(sysUserService.sysUserExist(query));
    }

    @ApiOperation("创建用户")
    @PostMapping(value = "/add")
    public CommonResult<Void> addSysUser(@Validated(SysUserEditVO.Add.class) @RequestBody SysUserEditVO editVO) {
        return CommonResult.success(sysUserService.addSysUser(editVO));
    }

    @ApiOperation("编辑用户")
    @PostMapping(value = "/upd")
    public CommonResult<Void> editSysUser(@Validated(SysUserEditVO.Edit.class) @RequestBody SysUserEditVO editVO) {
        return CommonResult.success(sysUserService.editSysUser(editVO));
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/upd-pwd")
    public CommonResult<Void> updSysUserPwd(@RequestBody SysUserUpdPwdVO userUpdPwdVO) {
        return CommonResult.success(sysUserService.updSysUserPwd(userUpdPwdVO));
    }

    @ApiOperation("用户详情")
    @PostMapping(value = "/info")
    public CommonResult<SysUserInfoVO> sysUserInfo(@Validated(SysUserEditVO.Info.class) @RequestBody SysUserEditVO editVO) {
        return CommonResult.success(sysUserService.sysUserInfo(editVO));
    }

    @ApiOperation("删除用户")
    @PostMapping(value = "/del")
    public CommonResult<Void> delSysUser(@Validated(SysUserEditVO.Del.class) @RequestBody SysUserEditVO editVO) {
        return CommonResult.success(sysUserService.delSysUser(editVO));
    }

    @GetMapping("/getUserInfo")
    public CommonResult<UserVO> getUserInfo() {
        return CommonResult.success(sysUserService.getUserInfo());
    }

}
