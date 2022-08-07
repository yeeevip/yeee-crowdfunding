package vip.yeee.app.crowdfunding.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.yeee.app.crowdfunding.manage.biz.CfProjectBiz;
import vip.yeee.app.crowdfunding.manage.model.request.IdsRequest;
import vip.yeee.app.crowdfunding.manage.model.vo.AuditProjectVO;
import vip.yeee.app.crowdfunding.manage.model.vo.LunchProjectVO;
import vip.yeee.app.crowdfunding.manage.model.vo.ProjectVO;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 22:38
 */
@RequiredArgsConstructor
@Api(tags = "众筹后台-众筹项目")
@RequestMapping("/manage/cf/project")
@RestController
public class CfProjectController {

    private final CfProjectBiz cfProjectBiz;

    @ApiOperation("管理员项目分页")
    @GetMapping("/page")
    public CommonResult<PageVO<ProjectVO>> getAdminPageList(String query) {
        return CommonResult.success(cfProjectBiz.getAdminPageList(query));
    }

    @ApiOperation("删除项目")
    @PostMapping(value = "/del")
    public CommonResult<Void> delCfProject(@Validated @RequestBody IdsRequest request) {
        return CommonResult.success(cfProjectBiz.delCfProject(request));
    }

    @ApiOperation("审核-项目详情")
    @GetMapping("/detail")
    public CommonResult<LunchProjectVO> getAdminProjectDetail(Integer id) {
        return CommonResult.success(cfProjectBiz.getAdminProjectDetail(id));
    }

    @ApiOperation("审核项目")
    @PostMapping("/audits")
    public CommonResult<Void> adminProjectAudits(@RequestBody AuditProjectVO auditProjectVO) {
        return CommonResult.success(cfProjectBiz.adminProjectAudits(auditProjectVO));
    }

}
