package vip.yeee.app.sys.manage.controller;

import org.springframework.web.bind.annotation.*;
import vip.yeee.app.sys.manage.model.vo.SysRoleHasSetVO;
import vip.yeee.app.sys.manage.model.vo.SysRoleVO;
import vip.yeee.app.sys.manage.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/28 18:12
 */
@Api(tags = "系统后台-系统角色管理")
@RequiredArgsConstructor
@RequestMapping("/manage/sys-role")
@RestController
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @ApiOperation("角色分页")
    @GetMapping(value = "/page")
    public CommonResult<PageVO<SysRoleVO>> sysRolePageList(String query) {
        return CommonResult.success(sysRoleService.sysRolePageList(query));
    }

    @ApiOperation("角色是否存在")
    @GetMapping(value = "/exist")
    public CommonResult<Boolean> sysRoleExist(String query) {
        return CommonResult.success(sysRoleService.sysRoleExist(query));
    }

    @ApiOperation("创建角色")
    @PostMapping(value = "/add")
    public CommonResult<Void> addSysRole(@Validated(SysRoleVO.Add.class) @RequestBody SysRoleVO editVO) {
        return CommonResult.success(sysRoleService.addSysRole(editVO));
    }

    @ApiOperation("编辑角色")
    @PostMapping(value = "/upd")
    public CommonResult<Void> editSysRole(@Validated(SysRoleVO.Edit.class) @RequestBody SysRoleVO editVO) {
        return CommonResult.success(sysRoleService.editSysRole(editVO));
    }

    @ApiOperation("角色详情")
    @PostMapping(value = "/info")
    public CommonResult<SysRoleVO> sysRoleInfo(@Validated(SysRoleVO.Info.class) @RequestBody SysRoleVO editVO) {
        return CommonResult.success(sysRoleService.sysRoleInfo(editVO));
    }

    @ApiOperation("删除角色")
    @PostMapping(value = "/del")
    public CommonResult<Void> delSysRole(@Validated(SysRoleVO.Del.class) @RequestBody SysRoleVO editVO) {
        return CommonResult.success(sysRoleService.delSysRole(editVO));
    }

    @ApiOperation("查询角色和及已设置的")
    @GetMapping(value = "/list-set")
    public CommonResult<SysRoleHasSetVO> sysRoleListAndHasSet(Integer userId) {
        return CommonResult.success(sysRoleService.sysRoleListAndHasSet(userId));
    }

}
