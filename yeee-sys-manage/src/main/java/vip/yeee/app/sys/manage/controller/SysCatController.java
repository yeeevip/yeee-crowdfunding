package vip.yeee.app.sys.manage.controller;

import org.springframework.web.bind.annotation.*;
import vip.yeee.app.sys.manage.model.vo.SysCatVO;
import vip.yeee.app.sys.manage.service.SysCatService;
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
@Api(tags = "系统后台-数据字典管理")
@RequiredArgsConstructor
@RequestMapping("/manage/sys-cat")
@RestController
public class SysCatController {

    private final SysCatService sysCatService;

    @ApiOperation("字典分页")
    @GetMapping(value = "/page")
    public CommonResult<PageVO<SysCatVO>> sysCatPageList(String query) {
        return CommonResult.success(sysCatService.sysCatPageList(query));
    }

    @ApiOperation("字典二级树结构")
    @GetMapping(value = "/item-tree")
    public CommonResult<PageVO<SysCatVO>> sysItemTreeList(String query) {
        return CommonResult.success(sysCatService.sysItemTreeList(query));
    }

    @ApiOperation("字典是否存在")
    @GetMapping(value = "/exist")
    public CommonResult<Boolean> sysCatExist(String query) {
        return CommonResult.success(sysCatService.sysCatExist(query));
    }

    @ApiOperation("创建字典")
    @PostMapping(value = "/add")
    public CommonResult<Void> addSysCat(@Validated(SysCatVO.Add.class) @RequestBody SysCatVO editVO) {
        return CommonResult.success(sysCatService.addSysCat(editVO));
    }

    @ApiOperation("编辑字典")
    @PostMapping(value = "/upd")
    public CommonResult<Void> editSysCat(@Validated(SysCatVO.Edit.class) @RequestBody SysCatVO editVO) {
        return CommonResult.success(sysCatService.editSysCat(editVO));
    }

    @ApiOperation("字典详情")
    @PostMapping(value = "/info")
    public CommonResult<SysCatVO> sysCatInfo(@Validated(SysCatVO.Info.class) @RequestBody SysCatVO editVO) {
        return CommonResult.success(sysCatService.sysCatInfo(editVO));
    }

    @ApiOperation("删除字典")
    @PostMapping(value = "/del")
    public CommonResult<Void> delSysCat(@Validated(SysCatVO.Del.class) @RequestBody SysCatVO editVO) {
        return CommonResult.success(sysCatService.delSysCat(editVO));
    }

}
