package vip.yeee.app.sys.manage.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vip.yeee.app.sys.manage.model.vo.SysDeptHasSetVO;
import vip.yeee.app.sys.manage.model.vo.SysDeptVO;
import vip.yeee.app.sys.manage.service.SysDeptService;
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
@Api(tags = "系统后台-组织机构管理")
@RequiredArgsConstructor
@RequestMapping("/manage/sys-dept")
@RestController
public class SysDeptController {

    private final SysDeptService sysDeptService;

    @ApiOperation("机构分页")
    @PreAuthorize("hasAuthority('sys:dept:page')")
    @GetMapping(value = "/page")
    public CommonResult<PageVO<SysDeptVO>> sysDeptPageList(String query) {
        return CommonResult.success(sysDeptService.sysDeptPageList(query));
    }

    @ApiOperation("机构是否存在")
    @PreAuthorize("hasAuthority('sys:dept:exist')")
    @GetMapping(value = "/exist")
    public CommonResult<Boolean> sysDeptExist(String query) {
        return CommonResult.success(sysDeptService.sysDeptExist(query));
    }

    @ApiOperation("创建机构")
    @PreAuthorize("hasAuthority('sys:dept:add')")
    @PostMapping(value = "/add")
    public CommonResult<Void> addSysDept(@Validated(SysDeptVO.Add.class) @RequestBody SysDeptVO editVO) {
        return CommonResult.success(sysDeptService.addSysDept(editVO));
    }

    @ApiOperation("编辑机构")
    @PreAuthorize("hasAuthority('sys:dept:upd')")
    @PostMapping(value = "/upd")
    public CommonResult<Void> editSysDept(@Validated(SysDeptVO.Edit.class) @RequestBody SysDeptVO editVO) {
        return CommonResult.success(sysDeptService.editSysDept(editVO));
    }

    @ApiOperation("机构详情")
    @PreAuthorize("hasAuthority('sys:dept:info')")
    @PostMapping(value = "/info")
    public CommonResult<SysDeptVO> sysDeptInfo(@Validated(SysDeptVO.Info.class) @RequestBody SysDeptVO editVO) {
        return CommonResult.success(sysDeptService.sysDeptInfo(editVO));
    }

    @ApiOperation("删除机构")
    @PreAuthorize("hasAuthority('sys:dept:del')")
    @PostMapping(value = "/del")
    public CommonResult<Void> delSysDept(@Validated(SysDeptVO.Del.class) @RequestBody SysDeptVO editVO) {
        return CommonResult.success(sysDeptService.delSysDept(editVO));
    }

    @ApiOperation("查询组织机构及已设置的")
    @PreAuthorize("hasAuthority('sys:dept:listSet')")
    @GetMapping(value = "/list-set")
    public CommonResult<SysDeptHasSetVO> sysDeptListAndHasSet(Integer userId) {
        return CommonResult.success(sysDeptService.sysDeptListAndHasSet(userId));
    }

}
