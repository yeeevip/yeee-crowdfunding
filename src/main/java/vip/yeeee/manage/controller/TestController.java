package vip.yeeee.manage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.yeee.memo.integrate.common.model.rest.CommonResult;
import vip.yeee.memo.integrate.common.security.utils.SecurityUtil;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/28 16:51
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping
    public CommonResult<Object> testGet() {
        return CommonResult.success(SecurityUtil.currentSecurityUser());
    }

}
