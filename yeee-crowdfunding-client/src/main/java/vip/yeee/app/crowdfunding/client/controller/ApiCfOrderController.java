package vip.yeee.app.crowdfunding.client.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.yeee.app.crowdfunding.client.biz.ApiCfOrderBiz;
import vip.yeee.app.crowdfunding.client.model.vo.*;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 19:34
 */
@Api(tags = "众筹前台-订单中心")
@RequiredArgsConstructor
@RequestMapping("/api/cf/order")
@RestController
public class ApiCfOrderController {

    private final ApiCfOrderBiz apiCfOrderBiz;

    @ApiOperation("我下的订单")
    @PostMapping("/buyer")
    public CommonResult<PageVO<BuyOrderVO>> getMyselfBuyOrderList(@RequestBody BuyOrderPageReqVO buyOrderPageReqVO) {
        return CommonResult.success(apiCfOrderBiz.getMyselfBuyOrderList(buyOrderPageReqVO));
    }

    @ApiOperation("下单-支持项目")
    @PostMapping("/create")
    public CommonResult<Void> frontCreateOrder(@Validated @RequestBody CreateOrderVO createOrderVO) {
        return CommonResult.success(apiCfOrderBiz.frontCreateOrder(createOrderVO));
    }

    @ApiOperation("支付订单")
    @PostMapping("/pay")
    public CommonResult<Void> frontPayOrder(@Validated @RequestBody PayVO payVO) {
        return CommonResult.success(apiCfOrderBiz.frontPayOrder(payVO));
    }

    @ApiOperation("确认收货")
    @PostMapping("/receive-confirm")
    public CommonResult<Void> frontConfirmReceiveOrder(@Validated @RequestBody PayVO payVO) {
        return CommonResult.success(apiCfOrderBiz.frontConfirmReceiveOrder(payVO));
    }

    @ApiOperation("发货")
    @PostMapping("/deliver")
    public CommonResult<Void> frontDeliverOrder(@Validated @RequestBody PayVO payVO) {
        return CommonResult.success(apiCfOrderBiz.frontDeliverOrder(payVO));
    }

    @ApiOperation("已卖出的订单")
    @PostMapping("/seller")
    public CommonResult<PageVO<SellerOrderVO>> getSellerOrderList(@RequestBody BuyOrderPageReqVO buyOrderPageReqVO) {
        return CommonResult.success(apiCfOrderBiz.getSellerOrderList(buyOrderPageReqVO));
    }

}
