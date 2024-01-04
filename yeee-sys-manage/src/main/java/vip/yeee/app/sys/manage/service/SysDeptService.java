package vip.yeee.app.sys.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import vip.yeee.app.sys.manage.convert.SysDeptConvert;
import vip.yeee.app.sys.manage.model.vo.SysDeptHasSetVO;
import vip.yeee.app.sys.manage.domain.mysql.mapper.SysDeptMapper;
import vip.yeee.app.sys.manage.domain.mysql.mapper.SysUserDeptMapper;
import vip.yeee.app.common.domain.mysql.entity.SysDept;
import vip.yeee.app.common.domain.mysql.entity.SysUserDept;
import vip.yeee.app.sys.manage.model.vo.SysDeptVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.base.model.vo.PageVO;
import vip.yeee.memo.base.mybatisplus.warpper.MyPageWrapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author yeeee
 * @since 2022-05-29
 */
@RequiredArgsConstructor
@Service
public class SysDeptService extends ServiceImpl<SysDeptMapper, SysDept> {

    private final SysDeptConvert sysDeptConvert;

    private final SysUserDeptMapper sysUserDeptMapper;

    public PageVO<SysDeptVO> sysDeptPageList(String query) {
        MyPageWrapper<SysDept> pageWrapper = new MyPageWrapper<>(query);
        pageWrapper.getPage().setSize(1000);
        IPage<SysDept> page = this.page(pageWrapper.getPage(), pageWrapper.getQueryWrapper());
        List<SysDeptVO> deptVOList = page.getRecords()
                .stream()
                .map(sysDeptConvert::entity2VO)
                .collect(Collectors.toList());
        Map<Integer, List<SysDeptVO>> pidMap = deptVOList.stream().filter(item -> item.getPid() != null && item.getPid() !=-1).collect(Collectors.groupingBy(SysDeptVO::getPid));
        List<SysDeptVO> rootList = Lists.newArrayList();
        deptVOList.forEach(item -> {
            if (item.getPid() == null || item.getPid() == -1) {
                this.tree(pidMap, item);
                rootList.add(item);
            }
        });
        return new PageVO<>((int)page.getCurrent(), (int)page.getSize(), (int)page.getPages(), page.getTotal(), rootList);
    }

    private void tree(Map<Integer, List<SysDeptVO>> pidMap, SysDeptVO curDept) {
        List<SysDeptVO> child = pidMap.get(curDept.getId());
        if (child == null) return;
        curDept.setChildren(child);
        child.forEach(item -> {
            tree(pidMap, item);
        });
    }

    public boolean sysDeptExist(String query) {
        MyPageWrapper<SysDept> pageWrapper = new MyPageWrapper<>(query);
        return this.count(pageWrapper.getQueryWrapper()) > 0;
    }

    public Void addSysDept(SysDeptVO editVO) {
        SysDept sysDept = sysDeptConvert.vo2Entity(editVO);
        this.save(sysDept);
        return null;
    }

    public Void editSysDept(SysDeptVO editVO) {
        SysDept sysDept = this.getById(editVO.getId());
        if (sysDept == null) {
            throw new BizException("组织不存在");
        }
        SysDept upd = sysDeptConvert.vo2Entity(editVO);
        this.updateById(upd);
        return null;
    }

    public SysDeptVO sysDeptInfo(SysDeptVO editVO) {
        SysDept sysDept = this.getById(editVO.getId());
        if (sysDept == null) {
            throw new BizException("字典不存在");
        }
        SysDeptVO sysDeptVO = sysDeptConvert.entity2VO(sysDept);
        if (sysDeptVO.getPid() != null) {
            SysDept pDept = this.getById(sysDeptVO.getPid());
            sysDeptVO.setPnm(Optional.ofNullable(pDept).orElseGet(SysDept::new).getName());
        }
        return sysDeptVO;
    }

    public Void delSysDept(SysDeptVO editVO) {
        this.removeByIds(editVO.getIds());
        return null;
    }

    public SysDeptHasSetVO sysDeptListAndHasSet(Integer userId) {
        SysDeptHasSetVO sysDeptHasSetVO = new SysDeptHasSetVO();
        List<SysUserDept> userDeptList = userId != null ? sysUserDeptMapper.selectList(Wrappers.<SysUserDept>lambdaQuery().eq(SysUserDept::getUserId, userId)) : Collections.emptyList();
        sysDeptHasSetVO.setCheckedKeys(userDeptList.stream().map(SysUserDept::getDeptId).collect(Collectors.toSet()));
        List<SysDeptVO> deptVOList = this.list()
                .stream()
                .map(sysDeptConvert::entity2VO)
                .collect(Collectors.toList());
        Map<Integer, List<SysDeptVO>> pidMap = deptVOList.stream().filter(item -> item.getPid() != null && item.getPid() !=-1).collect(Collectors.groupingBy(SysDeptVO::getPid));
        List<SysDeptVO> rootList = Lists.newArrayList();
        deptVOList.forEach(item -> {
            if (item.getPid() == null || item.getPid() == -1) {
                this.tree(pidMap, item);
                rootList.add(item);
            }
        });
        sysDeptHasSetVO.setList(rootList);
        return sysDeptHasSetVO;
    }
}
