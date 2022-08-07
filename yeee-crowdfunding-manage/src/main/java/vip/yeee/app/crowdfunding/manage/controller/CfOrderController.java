package vip.yeee.app.crowdfunding.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.yeee.app.crowdfunding.manage.biz.CfOrderBiz;
import vip.yeee.app.crowdfunding.manage.model.request.CfOrderEditRequest;
import vip.yeee.app.crowdfunding.manage.model.request.IdsRequest;
import vip.yeee.app.crowdfunding.manage.model.vo.SellerOrderVO;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 19:34
 */
@Api(tags = "众筹后台-订单中心")
@RequiredArgsConstructor
@RequestMapping("/manage/cf/order")
@RestController
public class CfOrderController {

    private final CfOrderBiz cfOrderBiz;

    @ApiOperation("订单分页")
    @GetMapping(value = "/page")
    public CommonResult<PageVO<SellerOrderVO>> cfOrderPageList(String query) {
        return CommonResult.success(cfOrderBiz.cfUserPageList(query));
    }

    @ApiOperation("订单详情")
    @PostMapping(value = "/info")
    public CommonResult<SellerOrderVO> cfOrderInfo(@Validated(CfOrderEditRequest.Info.class) @RequestBody CfOrderEditRequest request) {
        return CommonResult.success(cfOrderBiz.cfUserInfo(request));
    }

    @ApiOperation("删除订单")
    @PostMapping(value = "/del")
    public CommonResult<Void> cfOrderBiz(@RequestBody IdsRequest request) {
        return CommonResult.success(cfOrderBiz.delCfUser(request));
    }

}
