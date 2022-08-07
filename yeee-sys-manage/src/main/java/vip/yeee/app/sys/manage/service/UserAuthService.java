package vip.yeee.app.sys.manage.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import vip.yeee.app.common.constant.MessageConstant;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.base.websecurityoauth2.constant.AuthConstant;
import vip.yeee.memo.base.websecurityoauth2.model.AuthUser;
import vip.yeee.memo.base.websecurityoauth2.model.Oauth2TokenVo;
import vip.yeee.app.sys.manage.domain.mysql.mapper.SysRoleMapper;
import vip.yeee.app.sys.manage.domain.mysql.mapper.SysUserMapper;
import vip.yeee.app.sys.manage.domain.mysql.mapper.SysUserRoleMapper;
import vip.yeee.app.common.domain.mysql.entity.SysRole;
import vip.yeee.app.common.domain.mysql.entity.SysUser;
import vip.yeee.app.common.domain.mysql.entity.SysUserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.yeee.memo.common.platformauth.server.service.AbstractCustomUserDetailsService;

import javax.annotation.Resource;
import java.util.*;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/28 16:09
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserAuthService extends AbstractCustomUserDetailsService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    //private final UserMapper userMapper;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private OAuth2RestTemplate oAuth2RestTemplate;
    private final static String SPLIT_PATTERN = "##";

    @Override
    public AuthUser getSystemUserByUsername(String username) {
        SysUser sysUser = sysUserMapper.getOne(new SysUser().setUsername(username));
        if (sysUser == null) {
            throw new BizException(MessageConstant.USER_NOT_EXIST);
        }
        // find roles
        List<SysUserRole> userRoles = sysUserRoleMapper.getList(new SysUserRole().setUserId(sysUser.getId()));
        if (CollectionUtil.isEmpty(userRoles)) {
            throw new BizException(MessageConstant.USER_NO_ROLES);
        }
        Set<String> roles = Sets.newHashSet();
        userRoles.forEach(ur -> {
            SysRole role = sysRoleMapper.getOne(new SysRole().setId(ur.getRoleId()));
            if (role != null) {
                roles.add(role.getCode());
            }
        });
        AuthUser authUser = new AuthUser();
        authUser.setUserId(sysUser.getId().toString());
        authUser.setUsername(sysUser.getUsername());
        authUser.setPassword(sysUser.getPassword());
        authUser.setState(sysUser.getState());
        authUser.setRoles(roles);
        return authUser;
    }

    @Override
    public AuthUser getFrontUserByUsername(String s) {
        return null;
    }

    public Object userLogout(String token) {
        log.info("logout");
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        // 移除access_token
        tokenStore.removeAccessToken(accessToken);
        // 移除refresh_token
        if (accessToken.getRefreshToken() != null) {
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        return null;
    }

    public Oauth2TokenVo getUserAccessToken(String username, String password, String userType) {
        log.info("getUserAccessToken, userType = {}, username = {}, password = {}", userType, username, password);
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            throw new BizException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        ResourceOwnerPasswordResourceDetails passwordResourceDetails =
                (ResourceOwnerPasswordResourceDetails) this.oAuth2RestTemplate.getResource();
        passwordResourceDetails.setUsername(userType + SPLIT_PATTERN + username);
        passwordResourceDetails.setPassword(password);
        oAuth2RestTemplate.getOAuth2ClientContext().setAccessToken(null);
        OAuth2AccessToken accessToken = oAuth2RestTemplate.getAccessToken();
        Oauth2TokenVo oauth2TokenVo = new Oauth2TokenVo();
        oauth2TokenVo.setToken(accessToken.getValue());
        oauth2TokenVo.setRefreshToken(accessToken.getRefreshToken().getValue());
        oauth2TokenVo.setExpiresIn(accessToken.getExpiresIn());
        oauth2TokenVo.setTokenHead(AuthConstant.JWT_TOKEN_PREFIX);
        return oauth2TokenVo;
    }
}
