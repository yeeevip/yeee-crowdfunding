package vip.yeee.app.sys.manage.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import vip.yeee.app.common.constant.SysMenuTypeEnum;
import vip.yeee.app.common.domain.mysql.entity.*;
import vip.yeee.app.sys.manage.convert.SysMenuConvert;
import vip.yeee.app.sys.manage.domain.mysql.mapper.*;
import vip.yeee.app.sys.manage.model.dto.SysUserDeptDto;
import vip.yeee.app.sys.manage.model.dto.SysUserRoleDto;
import vip.yeee.app.sys.manage.model.vo.SysMenuHasSetVO;
import vip.yeee.app.sys.manage.model.vo.SysMenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.base.model.vo.PageVO;
import vip.yeee.memo.base.mybatisplus.warpper.MyPageWrapper;
import vip.yeee.memo.base.websecurityoauth2.context.SecurityContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/4 17:08
 */
@RequiredArgsConstructor
@Service
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> {

    private final SysMenuMapper sysMenuMapper;

    private final SysRoleMapper sysRoleMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final SysUserDeptMapper sysUserDeptMapper;

    private final SysMenuConvert sysMenuConvert;

    private final SysRoleMenuMapper sysRoleMenuMapper;

    public List<SysMenuVO> getMenuListTree() {

        List<SysMenu> sysMenuList;

        if (SecurityContext.isSuperAdmin()) {
            LambdaQueryWrapper<SysMenu> query = Wrappers.lambdaQuery();
            query.orderByAsc(SysMenu::getSeq);
            query.eq(SysMenu::getType, SysMenuTypeEnum.menu.getCode());
            sysMenuList = sysMenuMapper.selectList(query);
        } else {
            Integer userId = Integer.valueOf(SecurityContext.getCurUserId());
            List<SysUserRoleDto> userRoles = sysUserRoleMapper.getList(new SysUserRole().setUserId(userId));
            if (CollectionUtil.isEmpty(userRoles)) {
                return Collections.emptyList();
            }
            sysMenuList = sysMenuMapper.getListByRoleIds(userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()), SysMenuTypeEnum.menu.getCode());
        }

        if (CollectionUtil.isEmpty(sysMenuList)) {
            return Collections.emptyList();
        }

        List<SysMenuVO> sysMenuVOList = sysMenuList
                .stream()
                .map(sysMenuConvert::entity2VO)
                .collect(Collectors.toList());

        Map<Long, List<SysMenuVO>> pidMap = sysMenuVOList.stream().filter(item -> item.getPid() != null).collect(Collectors.groupingBy(SysMenuVO::getPid));

        List<SysMenuVO> parentMenu = Lists.newArrayList();
        sysMenuVOList.forEach(item -> {
            if (item.getPid() == null) {
                tree(pidMap, item);
                parentMenu.add(item);
            }
        });


        return parentMenu;
    }

    public PageVO<SysMenuVO> getSysMenuListTreeNode(String query) {
        MyPageWrapper<SysMenu> pageWrapper = new MyPageWrapper<>(query);
        QueryWrapper<SysMenu> queryWrapper = pageWrapper.getQueryWrapper();
        List<SysMenu> sysMenuList = this.list(queryWrapper);
        List<SysMenuVO> sysMenuVOList = sysMenuList
                .stream()
                .map(sysMenuConvert::entity2VO)
                .collect(Collectors.toList());
        Map<Long, List<SysMenuVO>> pidMap = sysMenuVOList.stream().filter(item -> item.getPid() != null).collect(Collectors.groupingBy(SysMenuVO::getPid));
        List<SysMenuVO> parentMenu = Lists.newArrayList();
        sysMenuVOList.forEach(item -> {
            if (item.getPid() == null) {
                tree(pidMap, item);
                parentMenu.add(item);
            }
        });
        return new PageVO<>(0, 0, 0, 0L, parentMenu);
    }

    public Map<String, List<String>> getMenuAuthz(Integer userId) {
        return getMenuAuthz(userId, SecurityContext.isSuperAdmin());
    }

    public Map<String, List<String>> getMenuAuthz(Integer userId, boolean isSuperAdmin) {
        List<String> roles = Lists.newArrayList();
        List<String> groups = Lists.newArrayList();
        List<String> stringPermissions = Lists.newArrayList();
        List<SysUserRoleDto> userRoles = sysUserRoleMapper.getList(new SysUserRole().setUserId(userId));
        roles.addAll(userRoles.stream().map(SysUserRoleDto::getRoleCode).collect(Collectors.toList()));
        List<SysUserDeptDto> userDeps = sysUserDeptMapper.getList(new SysUserDept().setUserId(userId));
        groups.addAll(userDeps.stream().map(SysUserDeptDto::getDeptCode).collect(Collectors.toList()));
        List<SysMenu> sysMenuList;
        if (isSuperAdmin) {
            LambdaQueryWrapper<SysMenu> query = Wrappers.lambdaQuery();
            query.orderByAsc(SysMenu::getSeq);
            query.eq(SysMenu::getType, SysMenuTypeEnum.func.getCode());
            sysMenuList = sysMenuMapper.selectList(query);
        } else {
            if (CollectionUtil.isNotEmpty(userRoles)) {
                sysMenuList = sysMenuMapper.getListByRoleIds(userRoles.stream()
                        .map(SysUserRole::getRoleId).collect(Collectors.toList()), SysMenuTypeEnum.func.getCode());
            } else {
                sysMenuList = Collections.emptyList();
            }
        }
        if (CollectionUtil.isNotEmpty(sysMenuList)) {
            sysMenuList.forEach(menu -> {
                if (StrUtil.isNotEmpty(menu.getPerm())) {
                    stringPermissions.addAll(StrUtil.split(menu.getPerm(), ','));
                }
            });
        }
        return ImmutableMap.of("roles", roles, "groups", groups, "stringPermissions", stringPermissions);
    }

    public Void addSysMenu(SysMenuVO editVO) {
        SysMenu sysMenu = sysMenuConvert.vo2Entity(editVO);
        this.save(sysMenu);
        return null;
    }

    public Void editSysMenu(SysMenuVO editVO) {
        SysMenu sysMenu = this.getById(editVO.getId());
        if (sysMenu == null) {
            throw new BizException("菜单不存在");
        }
        if (editVO.getId().equals(editVO.getPid())) {
            editVO.setPid(null);
        }
        SysMenu upd = sysMenuConvert.vo2Entity(editVO);
        this.updateById(upd);
        return null;
    }

    public SysMenuVO sysMenuInfo(SysMenuVO editVO) {
        SysMenu sysMenu = this.getById(editVO.getId());
        if (sysMenu == null) {
            throw new BizException("菜单不存在");
        }
        SysMenuVO sysMenuVO = sysMenuConvert.entity2VO(sysMenu);
        if (sysMenuVO.getPid() != null) {
            SysMenu pMenu = this.getById(sysMenuVO.getPid());
            sysMenuVO.setPnm(Optional.ofNullable(pMenu).orElseGet(SysMenu::new).getName());
        }
        return sysMenuVO;
    }

    public Void delSysMenu(SysMenuVO editVO) {
        this.removeByIds(editVO.getIds());
        return null;
    }

    public SysMenuHasSetVO sysMenuListAndHasSet(Integer roleId) {
        SysMenuHasSetVO sysMenuHasSetVO = new SysMenuHasSetVO();
        List<SysRoleMenu> roleMenuList = roleId != null ? sysRoleMenuMapper.getList(new SysRoleMenu().setRoleId(roleId)) : Collections.emptyList();
        sysMenuHasSetVO.setCheckedKeys(roleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet()));
        List<SysMenu> sysMenuList = this.list();
        List<SysMenuVO> sysMenuVOList = sysMenuList
                .stream()
                .map(sysMenuConvert::entity2VO)
                .collect(Collectors.toList());
        Map<Long, List<SysMenuVO>> pidMap = sysMenuVOList.stream().filter(item -> item.getPid() != null).collect(Collectors.groupingBy(SysMenuVO::getPid));
        List<SysMenuVO> parentMenu = Lists.newArrayList();
        sysMenuVOList.forEach(item -> {
            if (item.getPid() == null) {
                tree(pidMap, item);
                parentMenu.add(item);
            }
        });
        sysMenuHasSetVO.setList(parentMenu);
        return sysMenuHasSetVO;
    }

    private void tree(Map<Long, List<SysMenuVO>> pidMap, SysMenuVO curMenu) {
        List<SysMenuVO> child = pidMap.get(curMenu.getId());
        if (child == null) return;
        curMenu.setChildren(child);
        child.forEach(item -> {
            tree(pidMap, item);
        });
    }

}
