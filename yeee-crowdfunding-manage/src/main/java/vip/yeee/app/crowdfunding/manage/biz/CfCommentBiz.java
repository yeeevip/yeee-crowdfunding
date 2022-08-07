package vip.yeee.app.crowdfunding.manage.biz;

import org.springframework.stereotype.Component;
import vip.yeee.app.crowdfunding.manage.service.CfCommentService;

import javax.annotation.Resource;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/2/7 16:57
 */
@Component
public class CfCommentBiz {

    @Resource
    private CfCommentService cfCommentService;

}
