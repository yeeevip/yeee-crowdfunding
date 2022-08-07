package vip.yeeee.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import vip.yeee.memo.integrate.common.model.exception.BizException;
import vip.yeee.memo.integrate.common.security.config.AuthProperties;
import vip.yeee.memo.integrate.common.security.constant.AuthConstant;
import vip.yeee.memo.integrate.common.security.model.Oauth2TokenDTO;
import vip.yeee.memo.integrate.common.security.model.SecurityUser;
import vip.yeee.memo.integrate.common.security.service.CustomUserDetailsService;
import vip.yeeee.manage.domain.mysql.mapper.SysRoleMapper;
import vip.yeeee.manage.domain.mysql.mapper.SysUserMapper;
import vip.yeeee.manage.domain.mysql.mapper.SysUserRoleMapper;
import vip.yeeee.manage.constant.MessageConstant;
import vip.yeeee.manage.domain.mysql.entity.SysRole;
import vip.yeeee.manage.domain.mysql.entity.SysUser;
import vip.yeeee.manage.domain.mysql.entity.SysUserRole;
import vip.yeeee.manage.constant.UserBizErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
public class CustomUserServiceImpl implements CustomUserDetailsService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    //private final UserMapper userMapper;
    private final AuthProperties authProperties;

    @Override
    public UserDetails loadUserByUsername(String username, String userType) throws UsernameNotFoundException {

        SecurityUser securityUser = null;

        if ("SYSTEM".equals(userType)) {
            // find user
            SysUser sysUser = sysUserMapper.getOne(new SysUser().setUsername(username));
            if (sysUser == null) {
                throw new BizException(MessageConstant.USER_NOT_EXIST);
            }
            // find roles
            List<SysUserRole> userRoles = sysUserRoleMapper.getList(new SysUserRole().setUserId(sysUser.getId()));
            if (CollectionUtil.isEmpty(userRoles)) {
                throw new BizException(MessageConstant.USER_NO_ROLES);
            }
            Set<String> roles = Sets.newHashSet("SYSTEM-USER");
            userRoles.forEach(ur -> {
                SysRole role = sysRoleMapper.getOne(new SysRole().setId(ur.getRoleId()));
                if (role != null) {
                    roles.add(role.getCode());
                }
            });
            // build security-user
            securityUser = new SecurityUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), sysUser.getState(), roles);
        } else {
            // find user
//            User user = userMapper.getOne(new User().setUsername(username));
//            if (user == null) {
//                throw new BizException(MessageConstant.USER_NOT_EXIST);
//            }
//            // build security-user
//            securityUser = new SecurityUser(user.getId(), user.getUsername(), user.getPassword(), 0, Sets.newHashSet("FRONT-USER"));
        }

        // check
        if (securityUser == null) {
            throw new BizException(MessageConstant.USER_NOT_EXIST);
        } else if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }

        return securityUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    @Override
    public Oauth2TokenDTO oauthToken(String username, String password, String userType) {

        Assert.isTrue(CharSequenceUtil.isAllNotBlank(username, password), UserBizErrorCode.NAME_PASSWORD_NOT_EMPTY.getMessage());

        // build auth params
        Map<String, Object> params = new HashMap<>(5);
        params.put(AuthConstant.AUTH_CLIENT_ID, authProperties.getClientId());
        params.put(AuthConstant.AUTH_CLIENT_SECRET, authProperties.getClientSecret());
        params.put(AuthConstant.AUTH_GRANT_TYPE, authProperties.getGrantType());
        params.put(AuthConstant.AUTH_USER_TYPE, userType);
        params.put(AuthConstant.AUTH_USERNAME, username);
        params.put(AuthConstant.AUTH_PASSWORD, password);

        // http request
        String body = HttpUtil.post(authProperties.getUri(), params);
        JSONObject oAuth2AccessToken = JSON.parseObject(body);

        if (StrUtil.isBlank(oAuth2AccessToken.getString("access_token"))) {
            log.error("oauthToken err，body = {}", body);
            throw new BizException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }

        // convert
        Oauth2TokenDTO oauth2TokenDTO = new Oauth2TokenDTO();
        oauth2TokenDTO.setToken(oAuth2AccessToken.getString("access_token"));
        oauth2TokenDTO.setRefreshToken(oAuth2AccessToken.getString("refresh_token"));
        oauth2TokenDTO.setExpiresIn(oAuth2AccessToken.getInteger("expires_in"));
        oauth2TokenDTO.setTokenHead(AuthConstant.JWT_TOKEN_PREFIX);
        return oauth2TokenDTO;

    }

}
