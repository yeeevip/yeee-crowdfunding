package vip.yeee.app.sys.manage.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import vip.yeee.app.sys.manage.model.vo.*;
import vip.yeee.app.sys.manage.convert.SysUserConvert;
import vip.yeee.app.sys.manage.domain.mysql.mapper.SysUserDeptMapper;
import vip.yeee.app.sys.manage.domain.mysql.mapper.SysUserMapper;
import vip.yeee.app.sys.manage.domain.mysql.mapper.SysUserRoleMapper;
import vip.yeee.app.common.domain.mysql.entity.SysUser;
import vip.yeee.app.common.domain.mysql.entity.SysUserDept;
import vip.yeee.app.common.domain.mysql.entity.SysUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.base.model.vo.PageVO;
import vip.yeee.memo.base.mybatisplus.warpper.MyPageWrapper;
import vip.yeee.memo.base.websecurityoauth2.constant.SecurityUserTypeEnum;
import vip.yeee.memo.base.websecurityoauth2.context.SecurityContext;
import vip.yeee.memo.base.websecurityoauth2.model.Oauth2TokenVo;

import java.util.Date;
import java.util.List;
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
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    private final SysUserMapper sysUserMapper;

    private final SysUserConvert sysUserConvert;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final SysUserDeptMapper sysUserDeptMapper;

    private final PasswordEncoder passwordEncoder;

    private final CustomUserDetailsService customUserDetailsService;

    public Oauth2TokenVo login(String username, String password) {
        return customUserDetailsService.oauthToken(SecurityUserTypeEnum.SYSTEM_USER.getType(), username, password);
    }

    public Void userLogout() {
        customUserDetailsService.logout();
        return null;
    }

    public PageVO<UserVO> sysUserPageList(SysUserPageReqVO sysUserPageReqVO) {
        IPage<SysUser> page = sysUserMapper.getList(new Page<>(sysUserPageReqVO.getPageNum(), sysUserPageReqVO.getPageSize()), new SysUser());
        List<UserVO> userVOList = page.getRecords()
                .stream()
                .map(sysUserConvert::sysUser2VO)
                .collect(Collectors.toList());
        return new PageVO<>((int) page.getCurrent(), (int) page.getSize(), (int) page.getPages(), page.getTotal(), userVOList);
    }

    public PageVO<UserVO> sysUserPageList(String query) {
        MyPageWrapper<SysUser> pageWrapper = new MyPageWrapper<>(query);
        IPage<SysUser> page = this.page(pageWrapper.getPage(), pageWrapper.getQueryWrapper());
        List<UserVO> userVOList = page.getRecords()
                .stream()
                .map(sysUserConvert::sysUser2VO)
                .collect(Collectors.toList());
        return new PageVO<>((int)page.getCurrent(), (int)page.getSize(), (int)page.getPages(), page.getTotal(), userVOList);
    }

    public boolean sysUserExist(String query) {
        MyPageWrapper<SysUser> pageWrapper = new MyPageWrapper<>(query);
        return this.count(pageWrapper.getQueryWrapper()) > 0;
    }

    public UserVO getUserInfo() {
        UserVO userVO = sysUserConvert.securityUser2VO(SecurityContext.getCurUser());
        return userVO;
    }

    @Transactional(rollbackFor = Exception.class)
    public Void addSysUser(SysUserEditVO editVO) {
        SysUser sysUser = sysUserConvert.editVO2Entity(editVO);
        sysUser.setPassword(passwordEncoder.encode(SecureUtil.md5("111111")));
        this.save(sysUser);
        this.setUserRoles(editVO.getRoleIds(), sysUser.getId());
        this.setUserDepts(editVO.getOrgIds(), sysUser.getId());
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
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
                    String username = SecurityContext.getCurUser().getUsername();
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

    public SysUserInfoVO sysUserInfo(SysUserEditVO editVO) {
        SysUser sysUser = this.getById(editVO.getId());
        if (sysUser == null) {
            throw new BizException("用户不存在");
        }
        return sysUserConvert.entity2InfoVO(sysUser);
    }

    public Void delSysUser(SysUserEditVO editVO) {
        if (editVO.getIds().contains(Integer.parseInt(SecurityContext.getCurUserId()))) {
            throw new BizException("不能删除自己！");
        }
        this.removeByIds(editVO.getIds());
        return null;
    }

    public Void updSysUserPwd(SysUserUpdPwdVO userUpdPwdVO) {
        SysUser sysUser = this.getById(SecurityContext.getCurUserId());
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
            customUserDetailsService.logout();
        }
        return null;
    }

}
