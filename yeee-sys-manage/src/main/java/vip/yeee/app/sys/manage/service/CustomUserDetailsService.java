package vip.yeee.app.sys.manage.service;

import cn.hutool.core.util.StrUtil;
import vip.yeee.app.common.constant.MessageConstant;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.base.websecurityoauth2.constant.SecurityUserTypeEnum;
import vip.yeee.memo.base.websecurityoauth2.model.AuthUser;
import vip.yeee.app.sys.manage.domain.mysql.mapper.SysUserMapper;
import vip.yeee.app.common.domain.mysql.entity.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.yeee.memo.common.platformauth.server.service.AbstractCustomUserDetailsService;

import java.util.*;
import java.util.stream.Collectors;

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
    //private final UserMapper userMapper;
    private final SysMenuService sysMenuService;

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

        AuthUser authUser = new AuthUser();
        authUser.setUserId(sysUser.getId().toString());
        authUser.setUsername(sysUser.getUsername());
        authUser.setPassword(sysUser.getPassword());
        authUser.setState(sysUser.getState());
        authUser.setSuperAdmin(sysUser.getSuperAdmin());

        Map<String, List<String>> menuAuthz = sysMenuService.getMenuAuthz(sysUser.getId()
                , Integer.valueOf(1).equals(sysUser.getSuperAdmin()));
        authUser.setRoles(new HashSet<>(menuAuthz.get("roles")).stream().filter(StrUtil::isNotBlank).collect(Collectors.toSet()));
        authUser.setGroups(new HashSet<>(menuAuthz.get("groups")).stream().filter(StrUtil::isNotBlank).collect(Collectors.toSet()));
        authUser.setPermissions(new HashSet<>(menuAuthz.get("stringPermissions")).stream().filter(StrUtil::isNotBlank).collect(Collectors.toSet()));

        return authUser;
    }
}
