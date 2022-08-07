package vip.yeeee.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import vip.yeee.memo.integrate.common.model.exception.BizException;
import vip.yeee.memo.integrate.common.model.vo.PageVO;
import vip.yeee.memo.integrate.common.mybatisplus.warpper.MyPageWrapper;
import vip.yeee.memo.integrate.common.security.model.Oauth2TokenDTO;
import vip.yeee.memo.integrate.common.security.service.CustomUserDetailsService;
import vip.yeee.memo.integrate.common.security.utils.SecurityUtil;
import vip.yeeee.manage.convert.SysUserConvert;
import vip.yeeee.manage.domain.mysql.mapper.SysUserDeptMapper;
import vip.yeeee.manage.domain.mysql.mapper.SysUserMapper;
import vip.yeeee.manage.domain.mysql.mapper.SysUserRoleMapper;
import vip.yeeee.manage.domain.mysql.entity.SysUser;
import vip.yeeee.manage.domain.mysql.entity.SysUserDept;
import vip.yeeee.manage.domain.mysql.entity.SysUserRole;
import vip.yeeee.manage.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.yeeee.manage.model.vo.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 16:32
 */
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final CustomUserDetailsService userDetailsService;

    private final SysUserMapper sysUserMapper;

    private final SysUserConvert sysUserConvert;

    private final PasswordEncoder passwordEncoder;

    private final TokenStore tokenStore;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final SysUserDeptMapper sysUserDeptMapper;

    @Override
    public Oauth2TokenDTO login(String username, String password) {
        return userDetailsService.oauthToken(username, password, "SYSTEM");
    }

    @Override
    public PageVO<UserVO> sysUserPageList(SysUserPageReqVO sysUserPageReqVO) {
        IPage<SysUser> page = sysUserMapper.getList(new Page<>(sysUserPageReqVO.getPageNum(), sysUserPageReqVO.getPageSize()), new SysUser());
        List<UserVO> userVOList = page.getRecords()
                .stream()
                .map(sysUserConvert::sysUser2VO)
                .collect(Collectors.toList());
        return new PageVO<>((int) page.getCurrent(), (int) page.getSize(), (int) page.getPages(), page.getTotal(), userVOList);
    }

    @Override
    public PageVO<UserVO> sysUserPageList(String query) {
        MyPageWrapper<SysUser> pageWrapper = new MyPageWrapper<>(query);
        IPage<SysUser> page = this.page(pageWrapper.getPage(), pageWrapper.getQueryWrapper());
        List<UserVO> userVOList = page.getRecords()
                .stream()
                .map(sysUserConvert::sysUser2VO)
                .collect(Collectors.toList());
        return new PageVO<>((int)page.getCurrent(), (int)page.getSize(), (int)page.getPages(), page.getTotal(), userVOList);
    }

    @Override
    public boolean sysUserExist(String query) {
        MyPageWrapper<SysUser> pageWrapper = new MyPageWrapper<>(query);
        return this.count(pageWrapper.getQueryWrapper()) > 0;
    }

    @Override
    public UserVO getUserInfo() {
        UserVO userVO = sysUserConvert.securityUser2VO(SecurityUtil.currentSecurityUser());
        return userVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Void addSysUser(SysUserEditVO editVO) {
        SysUser sysUser = sysUserConvert.editVO2Entity(editVO);
        sysUser.setPassword(passwordEncoder.encode("111111"));
        this.save(sysUser);
        this.setUserRoles(editVO.getRoleIds(), sysUser.getId());
        this.setUserDepts(editVO.getOrgIds(), sysUser.getId());
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Void editSysUser(SysUserEditVO editVO) {
        Integer userId = editVO.getId();
        SysUser sysUser = this.getById(userId);
        if (sysUser == null) {
            throw new BizException("用户不存在");
        }
        SysUser upd = sysUserConvert.editVO2Entity(editVO);
        this.updateById(upd);
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
        this.setUserRoles(editVO.getRoleIds(), sysUser.getId());
        sysUserDeptMapper.delete(Wrappers.<SysUserDept>lambdaQuery().eq(SysUserDept::getUserId, userId));
        this.setUserDepts(editVO.getOrgIds(), sysUser.getId());
        return null;
    }

    private void setUserRoles(Set<Integer> roleIds, Integer userId) {
        List<SysUserRole> userRoleList = roleIds
                .stream()
                .map(roleId -> {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(userId);
                    sysUserRole.setRoleId(roleId);
                    String username = SecurityUtil.currentSecurityUser().getUsername();
                    Date date = new Date();
                    sysUserRole.setCreateTime(date);
                    sysUserRole.setCreateBy(username);
                    sysUserRole.setUpdateTime(date);
                    sysUserRole.setUpdateBy(username);
                    return sysUserRole;
                })
                .collect(Collectors.toList());
        sysUserRoleMapper.batchInsert(userRoleList);
    }

    private void setUserDepts(Set<Integer> orgIds, Integer userId) {
        if (CollectionUtil.isEmpty(orgIds)) {
            return;
        }
        List<SysUserDept> userRoleList = orgIds
                .stream()
                .map(deptId -> {
                    SysUserDept sysUserDept = new SysUserDept();
                    sysUserDept.setUserId(userId);
                    sysUserDept.setDeptId(deptId);
                    return sysUserDept;
                })
                .collect(Collectors.toList());
        userRoleList.forEach(sysUserDeptMapper::insert);
    }

    @Override
    public SysUserInfoVO sysUserInfo(SysUserEditVO editVO) {
        SysUser sysUser = this.getById(editVO.getId());
        if (sysUser == null) {
            throw new BizException("用户不存在");
        }
        return sysUserConvert.entity2InfoVO(sysUser);
    }

    @Override
    public Void delSysUser(SysUserEditVO editVO) {
        this.removeByIds(editVO.getIds());
        return null;
    }

    @Override
    public Void updSysUserPwd(SysUserUpdPwdVO userUpdPwdVO) {
        SysUser sysUser = this.getById(SecurityUtil.currentUserId());
        if (sysUser == null) {
            throw new BizException("用户不存在");
        }
//        if (!userUpdPwdVO.getNewPassword1().equals(userUpdPwdVO.getNewPassword2())) {
//            throw new BizException("两次新密码前后不一致");
//        }
        if (!passwordEncoder.matches(userUpdPwdVO.getOldPassword(), sysUser.getPassword())) {
            throw new BizException("旧密码不正确");
        }
        SysUser upd = new SysUser();
        upd.setId(sysUser.getId());
        upd.setPassword(passwordEncoder.encode(userUpdPwdVO.getNewPassword()));
        boolean res = this.updateById(upd);
        if (res) {
            OAuth2AccessToken oAuth2AccessToken = SecurityUtil.getOAuth2AccessToken();
            if (oAuth2AccessToken != null) {
                tokenStore.removeAccessToken(oAuth2AccessToken);
            }
        }
        return null;
    }
}
