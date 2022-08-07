package vip.yeee.app.crowdfunding.client.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.yeee.app.crowdfunding.client.biz.ApiCfProjectBiz;
import vip.yeee.app.crowdfunding.client.model.vo.*;
import vip.yeee.memo.base.model.annotation.AnonymousAccess;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 22:38
 */
@RequiredArgsConstructor
@Api(tags = "众筹前台-众筹项目")
@RequestMapping("/api/cf/project")
@RestController
public class ApiCfProjectController {

    private final ApiCfProjectBiz apiCfProjectBiz;

    @ApiOperation("首页项目")
    @AnonymousAccess
    @GetMapping("/index")
    public CommonResult<IndexProjectListVO> getIndexShowProject() {
        return CommonResult.success(apiCfProjectBiz.getIndexShowProject());
    }

    @ApiOperation("项目详情")
    @AnonymousAccess
    @GetMapping("/detail")
    public CommonResult<ProjectDetailVO> getIndexProjectDetail(Integer id) {
        return CommonResult.success(apiCfProjectBiz.getIndexProjectDetail(id));
    }

    @ApiOperation("项目分页")
    @AnonymousAccess
    @PostMapping("/page")
    public CommonResult<PageVO<ProjectVO>> getProjectList(@RequestBody ProjectPageReqVO reqVO) {
        return CommonResult.success(apiCfProjectBiz.getProjectPageList(reqVO));
    }

    @ApiOperation("我发起的项目")
    @PostMapping("/myself")
    public CommonResult<PageVO<ProjectVO>> getMyselfProjectList(@RequestBody ProjectPageReqVO reqVO) {
        return CommonResult.success(apiCfProjectBiz.getMyselfProjectList(reqVO));
    }

    @ApiOperation("发起项目")
    @PostMapping("/lunch")
    public CommonResult<Void> lunchProject(@Validated @RequestBody LunchProjectVO reqVO) {
        return CommonResult.success(apiCfProjectBiz.lunchProject(reqVO));
    }

    @ApiOperation("发起项目")
    @PostMapping("/updateProgress")
    public CommonResult<Void> updateProjectProgress(@Validated @RequestBody ProjectProgressVO projectProgressVO) {
        return CommonResult.success(apiCfProjectBiz.updateProjectProgress(projectProgressVO));
    }

    @ApiOperation("修改项目上线、下线状态")
    @PostMapping("/updUpOrDown")
    public CommonResult<Void> updateProjectUpOrDown(@Validated @RequestBody ProjectUpOrDownVO request) {
        return CommonResult.success(apiCfProjectBiz.updateProjectUpOrDown(request));
    }

    @ApiOperation("项目下单预览页")
    @AnonymousAccess
    @GetMapping("/orderPage")
    public CommonResult<OrderPageVO> frontProjectOrderPageDetail(@RequestParam Integer id) {
        return CommonResult.success(apiCfProjectBiz.frontProjectOrderPageDetail(id));
    }

/*    @ApiOperation("项目发起页")
    @AnonymousAccess
    @GetMapping("front/page/private/lunch")
    public void goProjectLunchPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/pages/front/private/faqi.html").forward(request, response);
    }*/

}
