package vip.yeee.app.crowdfunding.client.biz;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.*;
import vip.yeee.app.crowdfunding.client.convert.*;
import vip.yeee.app.crowdfunding.client.model.vo.*;
import vip.yeee.app.crowdfunding.client.service.*;
import vip.yeee.app.crowdfunding.client.utils.BusinessUtils;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.base.model.vo.PageVO;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/29 16:30
 */
@Component
public class ApiCfOrderBiz {

    @Resource
    private ApiCfOrderService apiCfOrderService;
    @Resource
    private ApiCfProjectService apiCfProjectService;
    @Resource
    private ApiCfUserService apiCfUserService;
    @Resource
    private ApiCfProjectRepayService apiCfProjectRepayService;
    @Resource
    private ApiCfReceiveInformationService apiCfReceiveInformationService;
    @Resource
    private ApiCfUserAccountService apiCfUserAccountService;
    @Resource
    private ApiCfOrderConvert apiCfOrderConvert;
    @Resource
    private ApiCfProjectConvert apiCfProjectConvert;
    @Resource
    private ApiCfProjectRepayConvert apiCfProjectRepayConvert;
    @Resource
    private ApiCfUserConvert apiCfUserConvert;
    @Resource
    private ApiCfReceiveInfoConvert apiCfReceiveInfoConvert;

    public PageVO<BuyOrderVO> getMyselfBuyOrderList(BuyOrderPageReqVO buyOrderPageReqVO) {

        Page<CfOrder> page = PageHelper.startPage(buyOrderPageReqVO.getPageNum(), buyOrderPageReqVO.getPageSize());

        List<CfOrder> orderList = apiCfOrderService.getListByUserId(BusinessUtils.getCurUserId());

        List<BuyOrderVO> buyOrderVOS = Optional.ofNullable(orderList).orElseGet(Lists::newArrayList)
                .stream()
                .map(order -> {
                    BuyOrderVO buyOrderVO = apiCfOrderConvert.order2VO(order);
                    CfProject cfProject = apiCfProjectService.getById(order.getProjectId());
                    buyOrderVO.setProjectVO(Optional.ofNullable(apiCfProjectConvert.project2VO(cfProject)).orElseGet(ProjectVO::new));
                    CfProjectRepay cfProjectRepay = apiCfProjectRepayService.getById(order.getProjectRepayId());
                    buyOrderVO.setProjectRepayVO(Optional.ofNullable(apiCfProjectRepayConvert.projectRepay2VO(cfProjectRepay)).orElseGet(ProjectRepayVO::new));
                    CfUser cfUser = apiCfUserService.getById(order.getUserSeller());
                    buyOrderVO.setSellerVO(Optional.ofNullable(apiCfUserConvert.user2VO(cfUser)).orElseGet(UserVO::new));
                    return buyOrderVO;
                })
                .collect(Collectors.toList());

        return new PageVO<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), buyOrderVOS);
    }

    public Void frontCreateOrder(CreateOrderVO createOrderVO) {

        CfProjectRepay projectRepay = apiCfProjectRepayService.getById(createOrderVO.getRepayId());
        if (projectRepay == null) {
            throw new BizException("购买的项目不存在");
        }

        Integer currentUserId = BusinessUtils.getCurUserId();
        CfProject project = apiCfProjectService.getById(projectRepay.getProjectId());

        CfOrder order = new CfOrder();

        ReceiveInfoVO receiveInfoVO = createOrderVO.getReceiveInfoVO();
        if (receiveInfoVO.getId() != null) {
            order.setReceiveInformation(receiveInfoVO.getId());
        } else {
            CfReceiveInformation receiveInformation = new CfReceiveInformation();
            receiveInformation.setUserId(currentUserId);
            receiveInformation.setReceiver(receiveInfoVO.getReceiver());
            receiveInformation.setPhone(receiveInfoVO.getPhone());
            receiveInformation.setAddress(receiveInfoVO.getAddress());
            apiCfReceiveInformationService.save(receiveInformation);
            order.setReceiveInformation(receiveInformation.getId());
        }

        BigDecimal costBig = BigDecimal.valueOf(projectRepay.getMoney()).multiply(BigDecimal.valueOf(createOrderVO.getPayCount()));

        order.setCode(IdUtil.simpleUUID());
        order.setProjectId(projectRepay.getProjectId());
        order.setProjectRepayId(projectRepay.getId());
        order.setUserId(currentUserId);
        order.setCount(createOrderVO.getPayCount());
        order.setOrderDate(new Date());

        order.setUserSeller(project.getUserId());
        order.setPayPrice(costBig.floatValue());

        apiCfOrderService.save(order);

        return null;
    }

    public Void frontPayOrder(PayVO payVO) {

        CfOrder order = apiCfOrderService.getById(payVO.getSubjectId());
        if (order == null) {
            throw new BizException("订单不存在");
        }

        CfProject project = apiCfProjectService.getById(order.getProjectId());
        if (project == null) {
            throw new BizException("项目不存在");
        }

        CfUserAccount account = apiCfUserAccountService.getUserAccountByUserId(BusinessUtils.getCurUserId());
        if (account == null
                || NumberUtil.isGreater(BigDecimal.valueOf(order.getPayPrice()), BigDecimal.valueOf(account.getBalance()))) {
            throw new BizException("钱包余额不足");
        }

        apiCfOrderService.updateById(new CfOrder().setId(order.getId()).setHasPay(1).setPayTime(new Date()));

        CfProject updProject = new CfProject().setId(project.getId());
        updProject.setHasFundRaising(BigDecimal.valueOf(Optional.ofNullable(project.getHasFundRaising()).orElse(0)).add(BigDecimal.valueOf(order.getPayPrice())).intValue());
        apiCfProjectService.updateById(updProject);

        apiCfUserAccountService.increaseUserBalance(account, BigDecimal.valueOf(order.getPayPrice()).longValue() * (-1));

        return null;
    }

    public Void frontConfirmReceiveOrder(PayVO payVO) {
        CfOrder order = apiCfOrderService.getById(payVO.getSubjectId());
        if (order == null) {
            throw new BizException("订单不存在");
        }
        LambdaUpdateWrapper<CfOrder> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CfOrder::getId, payVO.getSubjectId());
        updateWrapper.set(CfOrder::getHasReceive, 1);
        apiCfOrderService.update(updateWrapper);
        return null;
    }

    public Void frontDeliverOrder(PayVO payVO) {
        CfOrder order = apiCfOrderService.getById(payVO.getSubjectId());
        if (order == null) {
            throw new BizException("订单不存在");
        }
        LambdaUpdateWrapper<CfOrder> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CfOrder::getId, payVO.getSubjectId());
        updateWrapper.set(CfOrder::getHasSend, 1);
        apiCfOrderService.update(updateWrapper);
        return null;
    }

    public PageVO<SellerOrderVO> getSellerOrderList(BuyOrderPageReqVO buyOrderPageReqVO) {

        Page<CfOrder> page = PageHelper.startPage(buyOrderPageReqVO.getPageNum(), buyOrderPageReqVO.getPageSize());
        List<CfOrder> orderList = apiCfOrderService.getListBySellerUserId(BusinessUtils.getCurUserId());
        List<SellerOrderVO> orderVOList = Optional.ofNullable(orderList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfOrderConvert::order2SellerVO)
                .peek(item -> {
                    CfUser user = apiCfUserService.getById(item.getUserId());
                    item.setBuyerVO(Optional.ofNullable(apiCfUserConvert.user2VO(user)).orElseGet(UserVO::new));
                    CfProject project = apiCfProjectService.getById(item.getProjectId());
                    item.setProjectVO(Optional.ofNullable(apiCfProjectConvert.project2VO(project)).orElseGet(ProjectVO::new));
                    CfReceiveInformation receiveInformation = apiCfReceiveInformationService.getById(item.getReceiveInformation());
                    item.setReceiveInfoVO(Optional.ofNullable(apiCfReceiveInfoConvert.entity2VO(receiveInformation)).orElseGet(ReceiveInfoVO::new));
                    CfProjectRepay projectRepay = apiCfProjectRepayService.getById(item.getProjectRepayId());
                    item.setRepayVO(Optional.ofNullable(apiCfProjectRepayConvert.projectRepay2VO(projectRepay)).orElseGet(ProjectRepayVO::new));
                })
                .collect(Collectors.toList());

        return new PageVO<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), orderVOList);
    }
}
