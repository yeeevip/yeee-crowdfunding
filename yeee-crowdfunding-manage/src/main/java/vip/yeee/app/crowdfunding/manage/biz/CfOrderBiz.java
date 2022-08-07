package vip.yeee.app.crowdfunding.manage.biz;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfOrder;
import vip.yeee.app.common.domain.mysql.entity.CfProject;
import vip.yeee.app.common.domain.mysql.entity.CfReceiveInformation;
import vip.yeee.app.crowdfunding.manage.convert.CfOrderConvert;
import vip.yeee.app.crowdfunding.manage.convert.CfProjectConvert;
import vip.yeee.app.crowdfunding.manage.convert.CfReceiveInfoConvert;
import vip.yeee.app.crowdfunding.manage.domain.mysql.mapper.CfReceiveInformationMapper;
import vip.yeee.app.crowdfunding.manage.model.request.CfOrderEditRequest;
import vip.yeee.app.crowdfunding.manage.model.request.IdsRequest;
import vip.yeee.app.crowdfunding.manage.model.vo.ProjectVO;
import vip.yeee.app.crowdfunding.manage.model.vo.ReceiveInfoVO;
import vip.yeee.app.crowdfunding.manage.model.vo.SellerOrderVO;
import vip.yeee.app.crowdfunding.manage.service.CfOrderService;
import vip.yeee.app.crowdfunding.manage.service.CfProjectService;
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
 * @since 2023/2/7 10:00
 */
@Component
public class CfOrderBiz {

    @Resource
    private CfOrderService cfOrderService;
    @Resource
    private CfProjectService cfProjectService;
    @Resource
    private CfReceiveInformationMapper cfReceiveInformationMapper;
    @Resource
    private CfOrderConvert cfOrderConvert;
    @Resource
    private CfProjectConvert cfProjectConvert;
    @Resource
    private CfReceiveInfoConvert cfReceiveInfoConvert;

    public PageVO<SellerOrderVO> cfUserPageList(String query) {
        MyPageWrapper<CfOrder> wrapper = new MyPageWrapper<>(query);
        IPage<CfOrder> page = cfOrderService.page(wrapper.getPage(), wrapper.getQueryWrapper());
        List<SellerOrderVO> orderVOList = Optional
                .ofNullable(page.getRecords())
                .orElseGet(Lists::newArrayList)
                .stream()
                .map(cfOrderConvert::order2SellerVO)
                .peek(item -> {
                    CfProject project = cfProjectService.getById(item.getProjectId());
                    item.setProjectVO(Optional.ofNullable(cfProjectConvert.project2VO(project)).orElseGet(ProjectVO::new));
                    CfReceiveInformation receiveInformation = cfReceiveInformationMapper.selectById(item.getReceiveInformation());
                    item.setReceiveInfoVO(Optional.ofNullable(cfReceiveInfoConvert.entity2VO(receiveInformation)).orElseGet(ReceiveInfoVO::new));
                })
                .collect(Collectors.toList());
        return new PageVO<>((int)page.getCurrent(), (int)page.getSize(), (int)page.getPages(), page.getTotal(), orderVOList);
    }

    public SellerOrderVO cfUserInfo(CfOrderEditRequest request) {
        CfOrder order = cfOrderService.getById(request.getId());
        if (order == null) {
            throw new BizException("订单不存在");
        }
        SellerOrderVO orderVO = cfOrderConvert.order2SellerVO(order);
        CfProject project = cfProjectService.getById(orderVO.getProjectId());
        orderVO.setProjectVO(Optional.ofNullable(cfProjectConvert.project2VO(project)).orElseGet(ProjectVO::new));
        CfReceiveInformation receiveInformation = cfReceiveInformationMapper.selectById(orderVO.getReceiveInformation());
        orderVO.setReceiveInfoVO(Optional.ofNullable(cfReceiveInfoConvert.entity2VO(receiveInformation)).orElseGet(ReceiveInfoVO::new));
        return orderVO;
    }

    public Void delCfUser(IdsRequest request) {
        if (CollectionUtil.isEmpty(request.getIds())) {
            return null;
        }
        cfOrderService.removeByIds(request.getIds());
        return null;
    }

}
