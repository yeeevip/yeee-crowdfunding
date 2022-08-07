package vip.yeee.app.crowdfunding.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.yeee.app.crowdfunding.manage.biz.CfUserBiz;
import vip.yeee.app.crowdfunding.manage.model.request.CfUserEditRequest;
import vip.yeee.app.crowdfunding.manage.model.request.IdsRequest;
import vip.yeee.app.crowdfunding.manage.model.vo.CfUserInfoVO;
import vip.yeee.app.crowdfunding.manage.model.vo.UserVO;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/28 22:53
 */
@Slf4j
@Api(tags = "众筹后台-众筹用户管理")
@RequiredArgsConstructor
@RequestMapping("/manage/cf/user")
@RestController
public class CfUserController {

    private final CfUserBiz cfUserBiz;

    @GetMapping(value = "/page")
    public CommonResult<PageVO<UserVO>> cfUserPageList(String query) {
        return CommonResult.success(cfUserBiz.cfUserPageList(query));
    }

    @ApiOperation("用户是否存在")
    @GetMapping(value = "/exist")
    public CommonResult<Boolean> cfUserExist(String query) {
        return CommonResult.success(cfUserBiz.cfUserExist(query));
    }

    @ApiOperation("创建用户")
    @PostMapping(value = "/add")
    public CommonResult<Void> addCfUser(@Validated(CfUserEditRequest.Add.class) @RequestBody CfUserEditRequest request) {
        return CommonResult.success(cfUserBiz.addCfUser(request));
    }

    @ApiOperation("编辑用户")
    @PostMapping(value = "/upd")
    public CommonResult<Void> editCfUser(@Validated(CfUserEditRequest.Edit.class) @RequestBody CfUserEditRequest request) {
        return CommonResult.success(cfUserBiz.editCfUser(request));
    }

    @ApiOperation("用户详情")
    @PostMapping(value = "/info")
    public CommonResult<CfUserInfoVO> cfUserInfo(@Validated(CfUserEditRequest.Info.class) @RequestBody CfUserEditRequest request) {
        return CommonResult.success(cfUserBiz.cfUserInfo(request));
    }

    @ApiOperation("删除用户")
    @PostMapping(value = "/del")
    public CommonResult<Void> delCfUser(@RequestBody IdsRequest request) {
        return CommonResult.success(cfUserBiz.delCfUser(request));
    }

}
