package vip.yeee.app.crowdfunding.manage.biz;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.*;
import vip.yeee.app.crowdfunding.manage.convert.*;
import vip.yeee.app.crowdfunding.manage.model.request.IdsRequest;
import vip.yeee.app.crowdfunding.manage.model.vo.*;
import vip.yeee.app.crowdfunding.manage.service.*;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.base.model.vo.PageVO;
import vip.yeee.memo.base.mybatisplus.warpper.MyPageWrapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/28 15:36
 */
@Component
public class CfProjectBiz {

    @Resource
    private CfProjectService cfProjectService;
    @Resource
    private CfProjectCategoryService cfProjectCategoryService;
    @Resource
    private CfProjectDetailService cfProjectDetailService;
    @Resource
    private CfProjectRepayService cfProjectRepayService;
    @Resource
    private CfInitiatorPersonInfoService cfInitiatorPersonInfoService;
    @Resource
    private CfUserService cfUserService;
    @Resource
    private CfInitiatorCompanyInfoService cfInitiatorCompanyInfoService;
    @Resource
    private CfProjectConvert cfProjectConvert;
    @Resource
    private CfUserConvert cfUserConvert;
    @Resource
    private CfProjectCategoryConvert cfProjectCategoryConvert;
    @Resource
    private CfProjectDetailConvert cfProjectDetailConvert;
    @Resource
    private CfProjectRepayConvert cfProjectRepayConvert;
    @Resource
    private CfInitiatorInfoVOConvert cfInitiatorInfoVOConvert;

    public PageVO<ProjectVO> getAdminPageList(String query) {
        MyPageWrapper<CfProject> pageWrapper = new MyPageWrapper<>(query);
        IPage<CfProject> page = cfProjectService.page(pageWrapper.getPage(), pageWrapper.getQueryWrapper());
        List<ProjectVO> result = Optional
                .ofNullable(page.getRecords())
                .orElseGet(Lists::newArrayList)
                .stream()
                .map(cfProjectConvert::project2VO)
                .peek(item -> {
                    CfProjectCategory projectCategory = cfProjectCategoryService.getById(item.getProjectType());
                    item.setCategoryVO(cfProjectCategoryConvert.entity2VO(Optional.ofNullable(projectCategory).orElseGet(CfProjectCategory::new)));
                    CfUser user = cfUserService.getById(item.getUserId());
                    item.setSeller(cfUserConvert.user2VO(Optional.ofNullable(user).orElseGet(CfUser::new)));
                })
                .collect(Collectors.toList());
        return new PageVO<>((int)page.getCurrent(), (int)page.getSize(), (int)page.getPages(), page.getTotal(), result);
    }

    public Void delCfProject(IdsRequest request) {
        if (CollectionUtil.isEmpty(request.getIds())) {
            return null;
        }
        cfProjectService.removeByIds(request.getIds());
        return null;
    }

    public LunchProjectVO getAdminProjectDetail(Integer id) {

        if (id == null) {
            throw new BizException("项目ID不能为空");
        }

        CfProject project = cfProjectService.getById(id);
        if (project == null) {
            throw new BizException("项目不存在");
        }

        LunchProjectVO lunchProjectVO = cfProjectConvert.project2LunchProjectVO(project);

        List<ProjectItemVO> projectItemVOS = cfProjectDetailService.getDetailByProjectId(project.getId())
                .stream()
                .map(cfProjectDetailConvert::detail2VO)
                .collect(Collectors.toList());
        lunchProjectVO.setItemVOList(projectItemVOS);

        List<ProjectRepayVO> repayVOList = cfProjectRepayService.getRepayByProjectId(project.getId())
                .stream()
                .map(cfProjectRepayConvert::projectRepay2VO)
                .collect(Collectors.toList());
        lunchProjectVO.setRepayVOList(repayVOList);

        CfInitiatorPersonInfo initiatorPersonInfo = cfInitiatorPersonInfoService.getInitiatorPersonInfo(project.getId());
        lunchProjectVO.setInitiatorPersonInfoVO(Optional.ofNullable(cfInitiatorInfoVOConvert.entity2PersonInfoVO(initiatorPersonInfo)).orElseGet(InitiatorPersonInfoVO::new));

        CfInitiatorCompanyInfo initiatorCompanyInfo = cfInitiatorCompanyInfoService.getInitiatorCompanyInfo(project.getId());
        lunchProjectVO.setInitiatorCompanyInfoVO(Optional.ofNullable(cfInitiatorInfoVOConvert.entity2CompanyInfoVO(initiatorCompanyInfo)).orElseGet(InitiatorCompanyInfoVO::new));

        return lunchProjectVO;
    }

    public Void adminProjectAudits(AuditProjectVO auditProjectVO) {
        CfProject cfProject = new CfProject();
        cfProject.setId(auditProjectVO.getProjectId());
        cfProject.setHasAudits(auditProjectVO.getHasAudits());
        cfProjectService.updateById(cfProject);
        return null;
    }
}
