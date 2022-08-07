package vip.yeee.app.crowdfunding.client.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.yeee.app.common.domain.mysql.entity.CfProvinceCityDistrict;
import vip.yeee.app.crowdfunding.client.biz.ApiCfCommonBiz;
import vip.yeee.memo.base.model.annotation.AnonymousAccess;
import vip.yeee.memo.base.model.rest.CommonResult;

import java.io.IOException;
import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/1 12:58
 */
@Slf4j
@Api(tags = "众筹前台-通用接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cf/general")
public class ApiCfCommonController {

    private final ApiCfCommonBiz apiCfCommonBiz;

    @ApiOperation("获取地区")
    @GetMapping("/region")
    public CommonResult<List<CfProvinceCityDistrict>> getRegion(Integer pid) {
        return CommonResult.success(apiCfCommonBiz.getRegion(pid));
    }

    @ApiOperation("上传")
    @AnonymousAccess
    @PostMapping("/upload")
    public CommonResult<String> upload(@RequestPart List<MultipartFile> file, @RequestParam String path) {
        return CommonResult.success(apiCfCommonBiz.localUpload(file, path));
    }

    @AnonymousAccess
    @ApiOperation("获取验证码")
    @GetMapping(value = "/check-code")
    public void getCheckCode() throws IOException {
        apiCfCommonBiz.getCheckCode();
    }

}
