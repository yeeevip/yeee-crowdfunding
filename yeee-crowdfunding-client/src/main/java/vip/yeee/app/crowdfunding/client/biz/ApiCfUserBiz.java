package vip.yeee.app.crowdfunding.client.biz;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfUser;
import vip.yeee.app.common.utils.CheckCodeUtil;
import vip.yeee.app.crowdfunding.client.convert.ApiCfUserConvert;
import vip.yeee.app.crowdfunding.client.model.vo.*;
import vip.yeee.app.crowdfunding.client.service.ApiCfUserService;
import vip.yeee.app.crowdfunding.client.utils.BusinessUtils;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.common.appauth.server.kit.JwtServerKit;
import vip.yeee.memo.common.appauth.server.model.vo.JTokenVo;

import javax.annotation.Resource;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/2/6 20:20
 */
@Component
public class ApiCfUserBiz {

    @Resource
    private ApiCfUserService apiCfUserService;
    @Resource
    private ApiCfUserConvert apiCfUserConvert;
    @Autowired
    private CheckCodeUtil checkCodeUtil;
    @Resource
    private JwtServerKit jwtServerKit;

    public UserLoginVo login(UserCheckVO userCheckVO) {
        if (!checkCodeUtil.checkCode(userCheckVO.getCode())) {
            throw new BizException("验证码错误");
        }
        CfUser cfUser = apiCfUserService.getByUsername(userCheckVO.getUsername());
        if (cfUser == null) {
            throw new BizException("用户不存在！");
        }
        if (!jwtServerKit.matchPassword(userCheckVO.getPassword(), cfUser.getPassword())) {
            throw new BizException("密码错误！");
        }
        UserBasicVO userBasicVO = new UserBasicVO();
        userBasicVO.setId(cfUser.getId());
        userBasicVO.setUsername(cfUser.getUsername());
        userBasicVO.setNickName(cfUser.getNickName());
        JTokenVo jTokenVo = jwtServerKit.createToken(JSON.toJSONString(userBasicVO));

        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setToken(jTokenVo.getAccessToken());
        userLoginVo.setExpiresIn(jTokenVo.getExpireIn());
        return userLoginVo;
    }

    public Void logout() {
        return null;
    }

    public Void register(UserCheckVO userCheckVO) {
        if (!checkCodeUtil.checkCode(userCheckVO.getCode())) {
            throw new BizException("验证码错误");
        }
        CfUser user = apiCfUserService.getByUsername(userCheckVO.getUsername());
        if (user != null) {
            throw new BizException("用户名已经存在！！！");
        }
        CfUser saveUser = new CfUser();
        saveUser.setUsername(userCheckVO.getUsername());
        saveUser.setPassword(jwtServerKit.encodePassword(userCheckVO.getPassword()));
        apiCfUserService.save(saveUser);
        return null;
    }

    public Void updateMyselfInfo(UserVO userVO) {

        Integer currentUserId = BusinessUtils.getCurUserId();

        CfUser user = apiCfUserConvert.vo2Entity(userVO);
        user.setId(currentUserId);

        apiCfUserService.updateById(user);

        return null;

    }

    public UserVO getMyselfInfo() {

        Integer currentUserId = BusinessUtils.getCurUserId();

        CfUser user = apiCfUserService.getById(currentUserId);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        return apiCfUserConvert.user2VO(user);

    }

    public Void updatePassword(UpdatePasswordVO updatePasswordVO) {

        CfUser user = apiCfUserService.getById(BusinessUtils.getCurUserId());
        if (user == null) {
            throw new BizException("用户不存在");
        }

        if (!jwtServerKit.matchPassword(updatePasswordVO.getOldPassword(), (user.getPassword()))) {
            throw new BizException("旧密码不正确");
        }

        CfUser upd = new CfUser();
        upd.setId(user.getId()).setPassword(jwtServerKit.encodePassword(updatePasswordVO.getNewPassword()));
        apiCfUserService.updateById(upd);

        this.logout();

        return null;
    }
}
