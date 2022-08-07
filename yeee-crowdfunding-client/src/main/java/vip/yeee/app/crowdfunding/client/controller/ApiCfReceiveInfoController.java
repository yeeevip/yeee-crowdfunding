package vip.yeee.app.crowdfunding.client.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.yeee.app.crowdfunding.client.biz.ApiCfReceiveInfoBiz;
import vip.yeee.app.crowdfunding.client.model.vo.ReceiveInfoVO;
import vip.yeee.app.crowdfunding.client.model.vo.ReceivePageReqVO;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/7 23:06
 */
@Api(tags = "众筹前台-收货地址")
@RequiredArgsConstructor
@RequestMapping("/api/cf/receiveInfo")
@RestController
public class ApiCfReceiveInfoController {

    private final ApiCfReceiveInfoBiz apiCfReceiveInfoBiz;

    @ApiOperation("获取收货地址")
    @PostMapping("/page")
    public CommonResult<PageVO<ReceiveInfoVO>> getReceivePageList(@RequestBody ReceivePageReqVO receivePageReqVO) {
        return CommonResult.success(apiCfReceiveInfoBiz.getReceivePageList(receivePageReqVO));
    }

    @ApiOperation("添加收货地址")
    @PostMapping("/add")
    public CommonResult<Void> addReceiveInfo(@Validated(ReceiveInfoVO.Add.class) @RequestBody ReceiveInfoVO receiveInfoVO) {
        return CommonResult.success(apiCfReceiveInfoBiz.addReceiveInfo(receiveInfoVO));
    }

    @ApiOperation("修改收货地址")
    @PostMapping("/update")
    public CommonResult<Void> updateReceiveInfo(@Validated(ReceiveInfoVO.Update.class) @RequestBody ReceiveInfoVO receiveInfoVO) {
        return CommonResult.success(apiCfReceiveInfoBiz.updateReceiveInfo(receiveInfoVO));
    }

}
