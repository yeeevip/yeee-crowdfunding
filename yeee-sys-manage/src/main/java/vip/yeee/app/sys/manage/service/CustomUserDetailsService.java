package vip.yeee.app.sys.manage.service;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Sets;
import vip.yeee.app.common.constant.MessageConstant;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.base.websecurityoauth2.constant.SecurityUserTypeEnum;
import vip.yeee.memo.base.websecurityoauth2.model.AuthUser;
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
public class CustomUserDetailsService extends AbstractCustomUserDetailsService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    //private final UserMapper userMapper;

    @Override
    public AuthUser getUserByUserTypeAndUsername(String userType, String username) {
        if (SecurityUserTypeEnum.SYSTEM_USER.getType().equals(userType)) {
            return this.getSystemUserByUsername(username);
        }
        return null;
    }

    private AuthUser getSystemUserByUsername(String username) {
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
}
