package vip.yeee.app.crowdfunding.client.biz;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vip.yeee.app.common.domain.mysql.entity.*;
import vip.yeee.app.crowdfunding.client.convert.*;
import vip.yeee.app.crowdfunding.client.model.vo.*;
import vip.yeee.app.crowdfunding.client.service.*;
import vip.yeee.app.crowdfunding.client.utils.BusinessUtils;
import vip.yeee.app.crowdfunding.client.utils.DateConvertUtil;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.base.model.vo.PageVO;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/29 17:37
 */
@Component
public class ApiCfProjectBiz {

    @Resource
    private ApiCfProjectService apiCfProjectService;
    @Resource
    private ApiCfProjectDetailService apiCfProjectDetailService;
    @Resource
    private ApiCfProjectRepayService apiCfProjectRepayService;
    @Resource
    private ApiCfProjectProgressService apiCfProjectProgressService;
    @Resource
    private ApiCfCommentService apiCfCommentService;
    @Resource
    private ApiCfInitiatorPersonInfoService apiCfInitiatorPersonInfoService;
    @Resource
    private ApiCfInitiatorCompanyInfoService apiCfInitiatorCompanyInfoService;
    @Resource
    private ApiCfReceiveInformationService apiCfReceiveInformationService;
    @Resource
    private ApiCfUserService apiCfUserService;
    @Resource
    private ApiCfProjectConvert apiCfProjectConvert;
    @Resource
    private ApiCfProjectDetailConvert apiCfProjectDetailConvert;
    @Resource
    private ApiCfProjectRepayConvert apiCfProjectRepayConvert;
    @Resource
    private ApiCfCommentConvert apiCfCommentConvert;
    @Resource
    private ApiCfProjectProgressConvert apiCfProjectProgressConvert;
    @Resource
    private ApiCfUserConvert apiCfUserConvert;
    @Resource
    private ApiCfInitiatorInfoVOConvert apiCfInitiatorInfoVOConvert;
    @Resource
    private ApiCfReceiveInfoConvert apiCfReceiveInfoConvert;

    public IndexProjectListVO getIndexShowProject() {

        IndexProjectListVO indexProjectListVO = new IndexProjectListVO();

        // 热门
        List<CfProject> hotList = apiCfProjectService.getOrderLimitList(new CfProject().setHasIndex(1)
                , ImmutableMap.of("orderField", "has_fund_raising", "orderSort", "desc", "limit", 3));
        List<ProjectVO> hotVOList = Optional.ofNullable(hotList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfProjectConvert::project2VO)
                .collect(Collectors.toList());
        indexProjectListVO.setHotList(hotVOList);


        // 公益
        List<CfProject> welfareList = apiCfProjectService.getOrderLimitList(new CfProject().setCategoryId(1)
                , ImmutableMap.of("orderField", "has_fund_raising", "orderSort", "desc", "limit", 3));
        List<ProjectVO> welfareVOList = Optional.ofNullable(welfareList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfProjectConvert::project2VO)
                .collect(Collectors.toList());
        indexProjectListVO.setWelfareList(welfareVOList);

        // 农业
        List<CfProject> agList = apiCfProjectService.getOrderLimitList(new CfProject().setCategoryId(2)
                , ImmutableMap.of("orderField", "has_fund_raising", "orderSort", "desc", "limit", 3));
        List<ProjectVO> agVOList = Optional.ofNullable(agList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfProjectConvert::project2VO)
                .collect(Collectors.toList());
        indexProjectListVO.setAgList(agVOList);

        // 出版
        List<CfProject> publishList = apiCfProjectService.getOrderLimitList(new CfProject().setCategoryId(3)
                , ImmutableMap.of("orderField", "has_fund_raising", "orderSort", "desc", "limit", 3));
        List<ProjectVO> publishVOList = Optional.ofNullable(publishList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfProjectConvert::project2VO)
                .collect(Collectors.toList());
        indexProjectListVO.setPublishList(publishVOList);

        // 艺术
        List<CfProject> artList = apiCfProjectService.getOrderLimitList(new CfProject().setCategoryId(4)
                , ImmutableMap.of("orderField", "has_fund_raising", "orderSort", "desc", "limit", 3));
        List<ProjectVO> artVOList = Optional.ofNullable(artList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfProjectConvert::project2VO)
                .collect(Collectors.toList());
        indexProjectListVO.setArtList(artVOList);

        return indexProjectListVO;
    }

    public PageVO<ProjectVO> getProjectPageList(ProjectPageReqVO reqVO) {
        Page<ProjectVO> page = PageHelper.startPage(reqVO.getPageNum(), reqVO.getPageSize());

        CfProject query = new CfProject();
        if (reqVO.getProjectVO() != null) {
            query.setKeyword(reqVO.getProjectVO().getKeyword())
                    .setCategoryId(reqVO.getProjectVO().getProjectType());
        }
        query.setHasAudits(1);
        query.setHasDown(0);
        List<CfProject> projectList = apiCfProjectService.getProjectList(query);
        List<ProjectVO> result = Optional.ofNullable(projectList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfProjectConvert::project2VO)
                .collect(Collectors.toList());
        return new PageVO<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), result);
    }

    public PageVO<ProjectVO> getMyselfProjectList(ProjectPageReqVO reqVO) {
        Page<ProjectVO> page = PageHelper.startPage(reqVO.getPageNum(), 5);

        CfProject query = new CfProject();
        query.setUserId(BusinessUtils.getCurUserId());

        List<CfProject> projectList = apiCfProjectService.getProjectList(query);
        List<ProjectVO> result = Optional.ofNullable(projectList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfProjectConvert::project2VO)
                .collect(Collectors.toList());
        return new PageVO<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), result);
    }

    public ProjectDetailVO getIndexProjectDetail(Integer id) {

        if (id == null) {
            throw new BizException("项目ID不能为空");
        }

        CfProject project = apiCfProjectService.getById(id);
        if (project == null) {
            throw new BizException("项目不存在");
        }

        ProjectDetailVO projectDetailVO = apiCfProjectConvert.project2DetailVO(project);

        List<CfProjectDetail> projectDetailList = apiCfProjectDetailService.getDetailByProjectId(project.getId());
        List<ProjectItemVO> projectItemVOS = Optional.ofNullable(projectDetailList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfProjectDetailConvert::detail2VO)
                .collect(Collectors.toList());
        projectDetailVO.setItemVOList(projectItemVOS);

        List<CfProjectRepay> projectRepayList = apiCfProjectRepayService.getRepayByProjectId(project.getId());
        List<ProjectRepayVO> repayVOList = Optional.ofNullable(projectRepayList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfProjectRepayConvert::projectRepay2VO)
                .collect(Collectors.toList());
        projectDetailVO.setRepayVOList(repayVOList);

        List<CfComment> cfCommentList = apiCfCommentService.getCommentListByProjectId(project.getId());
        List<CommentVO> commentVOList = Optional.ofNullable(cfCommentList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfCommentConvert::comment2VO)
                .collect(Collectors.toList());
        projectDetailVO.setCommentVOList(commentVOList);

        //orderMapper.getList(new Order().setProjectId(project.getId()))

        List<CfProjectProgress> projectProgressList = apiCfProjectProgressService.getListByProjectId(project.getId());
        List<ProjectProgressVO> progressVOList = Optional.ofNullable(projectProgressList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfProjectProgressConvert::progress2VO)
                .peek(item -> item.setPublishDateStr(DateConvertUtil.timeToNow(item.getPublishDate())))
                .collect(Collectors.toList());
        projectDetailVO.setProgressVOList(progressVOList);

        CfUser createUser = apiCfUserService.getById(project.getUserId());
        projectDetailVO.setSellerVO(Optional.ofNullable(apiCfUserConvert.user2VO(createUser)).orElseGet(UserVO::new));

        projectDetailVO.setLeftDays(DateConvertUtil.getLeftDays(project.getDaysRaising(), project.getLaunchDateRaising(), new Date()));

        return projectDetailVO;
    }

    @Transactional(rollbackFor = Exception.class)
    public Void lunchProject(LunchProjectVO reqVO) {

        CfProject project = apiCfProjectConvert.lunchProjectVOProject(reqVO);
        project.setLaunchDateRaising(new Date());
        project.setUserId(BusinessUtils.getCurUserId());
        apiCfProjectService.save(project);
        if (project.getId() == null) {
            throw new BizException("发起失败");
        }

        if ("个人".equals(reqVO.getShenfen())) {
            CfInitiatorPersonInfo initiatorPersonInfo = apiCfInitiatorInfoVOConvert.personInfoVO2Entity(reqVO.getInitiatorPersonInfoVO());
            initiatorPersonInfo.setProjectId(project.getId());
            apiCfInitiatorPersonInfoService.save(initiatorPersonInfo);
        } else {
            CfInitiatorCompanyInfo initiatorCompanyInfo = apiCfInitiatorInfoVOConvert.companyInfoVO2Entity(reqVO.getInitiatorCompanyInfoVO());
            initiatorCompanyInfo.setProjectId(project.getId());
            apiCfInitiatorCompanyInfoService.save(initiatorCompanyInfo);
        }

        if (CollectionUtil.isNotEmpty(reqVO.getItemVOList())) {
            List<CfProjectDetail> detailList = reqVO.getItemVOList()
                    .stream()
                    .map(apiCfProjectDetailConvert::vo2Entity)
                    .peek(item -> item.setProjectId(project.getId()))
                    .collect(Collectors.toList());
            apiCfProjectDetailService.saveBatch(detailList);
        }

        if (CollectionUtil.isNotEmpty(reqVO.getRepayVOList())) {
            List<CfProjectRepay> repayList = reqVO.getRepayVOList()
                    .stream()
                    .map(apiCfProjectRepayConvert::vo2Entity)
                    .peek(item -> item.setProjectId(project.getId()))
                    .collect(Collectors.toList());
            apiCfProjectRepayService.saveBatch(repayList);
        }

        return null;
    }

    public OrderPageVO frontProjectOrderPageDetail(Integer id) {

        CfProject project = apiCfProjectService.getById(id);
        if (project == null) {
            throw new BizException("项目不存在");
        }
        OrderPageVO orderPageVO = new OrderPageVO();

        List<CfProjectRepay> projectRepayList = apiCfProjectRepayService.getRepayByProjectId(project.getId());
        List<ProjectRepayVO> repayVOList = Optional.ofNullable(projectRepayList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfProjectRepayConvert::projectRepay2VO)
                .collect(Collectors.toList());
        orderPageVO.setRepayVOList(repayVOList);

        Integer currentUserId = BusinessUtils.getCurUserId();
        if (currentUserId != null) {
            CfReceiveInformation receiveInformation = apiCfReceiveInformationService.getDefaultReceiveInfoByUserId(currentUserId);
            orderPageVO.setReceiveInfoVO(Optional.ofNullable(apiCfReceiveInfoConvert.entity2VO(receiveInformation)).orElseGet(ReceiveInfoVO::new));
        }

        return orderPageVO;
    }

    public Void updateProjectProgress(ProjectProgressVO projectProgressVO) {

        CfProject project = apiCfProjectService.getById(projectProgressVO.getProjectId());
        if (project == null) {
            throw new BizException("项目不存在");
        }

        CfProjectProgress add = new CfProjectProgress();
        add.setProjectId(project.getId());
        add.setPublishDate(new Date());
        add.setPubUser(BusinessUtils.geCurtUsername());
        add.setContent(projectProgressVO.getContent());
        apiCfProjectProgressService.save(add);

        return null;
    }

    public Void updateProjectUpOrDown(ProjectUpOrDownVO request) {

        CfProject project = apiCfProjectService.getById(request.getId());
        if (project == null) {
            throw new BizException("项目不存在");
        }
        CfProject upd = new CfProject();
        upd.setId(request.getId());
        upd.setHasDown(request.getHasDown());
        apiCfProjectService.updateById(upd);
        return null;
    }

}
