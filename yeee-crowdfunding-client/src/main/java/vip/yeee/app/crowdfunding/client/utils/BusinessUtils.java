package vip.yeee.app.crowdfunding.client.utils;

import vip.yeee.memo.common.appauth.client.context.ApiSecurityContext;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/28 20:18
 */
public class BusinessUtils {
    public static Integer getCurUserId() {
        return Integer.valueOf(ApiSecurityContext.getCurUserId());
    }

    public static String geCurtUsername() {
        return ApiSecurityContext.getCurUser().getUsername();
    }
}
