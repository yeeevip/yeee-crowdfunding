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
        String curUserId = ApiSecurityContext.getCurUserId();
        return curUserId == null ? null : Integer.valueOf(curUserId);
    }

    public static String geCurtUsername() {
        return ApiSecurityContext.getCurUser().getUsername();
    }
}
