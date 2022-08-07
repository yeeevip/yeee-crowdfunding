package vip.yeee.app.crowdfunding.manage.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.yeee.app.crowdfunding.manage.biz.CfCommentBiz;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/1 10:14
 */
@RequiredArgsConstructor
@Api(tags = "众筹后台-评论接口")
@RequestMapping("/manage/cf/user")
@RestController
public class CfCommentController {

    private final CfCommentBiz cfCommentBiz;

}
