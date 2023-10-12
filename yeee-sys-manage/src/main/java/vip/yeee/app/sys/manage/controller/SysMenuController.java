package vip.yeee.app.sys.manage.controller;

import io.swagger.annotations.Api;
import vip.yeee.app.sys.manage.model.vo.SysMenuHasSetVO;
import vip.yeee.app.sys.manage.model.vo.SysMenuVO;
import vip.yeee.app.sys.manage.service.SysMenuService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;
import vip.yeee.memo.base.websecurityoauth2.context.SecurityContext;

import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/4 17:09
 */
@Api(tags = "系统后台-系统菜单")
@RequiredArgsConstructor
@RequestMapping("/manage/sys-menu")
@RestController
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @GetMapping("/listTree")
    public CommonResult<List<SysMenuVO>> getMenuListTree() {
        return CommonResult.success(sysMenuService.getMenuListTree());
    }

    @GetMapping("/list")
    public CommonResult<PageVO<SysMenuVO>> getSysMenuListTreeNode(String query) {
        return CommonResult.success(sysMenuService.getSysMenuListTreeNode(query));
    }

    @GetMapping("/getAuthz")
    public CommonResult<Object> getMenuAuthz() {
        Integer userId = Integer.valueOf(SecurityContext.getCurUserId());
        return CommonResult.success(sysMenuService.getMenuAuthz(userId));
    }

    @ApiOperation("创建菜单")
    @PostMapping(value = "/add")
    public CommonResult<Void> addSysMenu(@Validated(SysMenuVO.Add.class) @RequestBody SysMenuVO editVO) {
        return CommonResult.success(sysMenuService.addSysMenu(editVO));
    }

    @ApiOperation("编辑菜单")
    @PostMapping(value = "/upd")
    public CommonResult<Void> editSysMenu(@Validated(SysMenuVO.Edit.class) @RequestBody SysMenuVO editVO) {
        return CommonResult.success(sysMenuService.editSysMenu(editVO));
    }

    @ApiOperation("菜单详情")
    @PostMapping(value = "/info")
    public CommonResult<SysMenuVO> sysMenuInfo(@Validated(SysMenuVO.Info.class) @RequestBody SysMenuVO editVO) {
        return CommonResult.success(sysMenuService.sysMenuInfo(editVO));
    }

    @ApiOperation("删除菜单")
    @PostMapping(value = "/del")
    public CommonResult<Void> delSysMenu(@Validated(SysMenuVO.Del.class) @RequestBody SysMenuVO editVO) {
        return CommonResult.success(sysMenuService.delSysMenu(editVO));
    }

    @ApiOperation("查询菜单和及已设置的")
    @GetMapping(value = "/list-set")
    public CommonResult<SysMenuHasSetVO> sysMenuListAndHasSet(Integer roleId) {
        return CommonResult.success(sysMenuService.sysMenuListAndHasSet(roleId));
    }

}
