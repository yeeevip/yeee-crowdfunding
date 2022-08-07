package vip.yeee.app.crowdfunding.client.biz;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vip.yeee.app.common.domain.mysql.entity.CfReceiveInformation;
import vip.yeee.app.crowdfunding.client.convert.ApiCfReceiveInfoConvert;
import vip.yeee.app.crowdfunding.client.model.vo.ReceiveInfoVO;
import vip.yeee.app.crowdfunding.client.model.vo.ReceivePageReqVO;
import vip.yeee.app.crowdfunding.client.service.ApiCfReceiveInformationService;
import vip.yeee.app.crowdfunding.client.utils.BusinessUtils;
import vip.yeee.memo.base.model.vo.PageVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/29 21:32
 */
@Component
public class ApiCfReceiveInfoBiz {

    @Resource
    private ApiCfReceiveInformationService apiCfReceiveInformationService;
    @Resource
    private ApiCfReceiveInfoConvert apiCfReceiveInfoConvert;

    public PageVO<ReceiveInfoVO> getReceivePageList(ReceivePageReqVO receivePageReqVO) {

        Page<CfReceiveInformation> page = PageHelper.startPage(receivePageReqVO.getPageNum(), receivePageReqVO.getPageSize());

        List<CfReceiveInformation> informationList = apiCfReceiveInformationService.getListByUserId(BusinessUtils.getCurUserId());
        List<ReceiveInfoVO> infoVOList = Optional.ofNullable(informationList).orElseGet(Lists::newArrayList)
                .stream()
                .map(apiCfReceiveInfoConvert::entity2VO)
                .collect(Collectors.toList());

        return new PageVO<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), infoVOList);
    }

    @Transactional(rollbackFor = Exception.class)
    public Void updateReceiveInfo(ReceiveInfoVO receiveInfoVO) {

        List<CfReceiveInformation> informationList = apiCfReceiveInformationService.getListByUserIdAndDefault(BusinessUtils.getCurUserId(), 1);
        if (CollectionUtil.isNotEmpty(informationList)) {
            informationList.forEach(item -> apiCfReceiveInformationService.updateById(new CfReceiveInformation().setId(item.getId()).setSetDefault(0)));
        }

        apiCfReceiveInformationService.updateById(new CfReceiveInformation()
                .setId(receiveInfoVO.getId())
                .setSetDefault(receiveInfoVO.getSetDefault()));

        return null;
    }

    public Void addReceiveInfo(ReceiveInfoVO receiveInfoVO) {
        CfReceiveInformation save = apiCfReceiveInfoConvert.vo2Entity(receiveInfoVO);
        save.setUserId(BusinessUtils.getCurUserId());
        apiCfReceiveInformationService.save(save);
        return null;
    }

}
