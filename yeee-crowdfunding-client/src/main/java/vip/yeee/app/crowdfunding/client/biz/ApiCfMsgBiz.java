package vip.yeee.app.crowdfunding.client.biz;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfMsg;
import vip.yeee.app.common.model.request.PageRequest;
import vip.yeee.app.crowdfunding.client.convert.ApiCfMsgConvert;
import vip.yeee.app.crowdfunding.client.model.vo.IdsRequest;
import vip.yeee.app.crowdfunding.client.model.vo.MsgListVO;
import vip.yeee.app.crowdfunding.client.service.ApiCfMsgService;
import vip.yeee.app.crowdfunding.client.utils.BusinessUtils;
import vip.yeee.memo.base.model.vo.PageVO;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/29 21:50
 */
@Component
public class ApiCfMsgBiz {

    @Resource
    private ApiCfMsgService apiCfMsgService;

    public PageVO<MsgListVO> frontMsgPageList(PageRequest request) {
        LambdaQueryWrapper<CfMsg> query = Wrappers.lambdaQuery();
        query.orderByDesc(CfMsg::getId);
        query.eq(CfMsg::getTarget, BusinessUtils.getCurUserId());
        query.eq(CfMsg::getHasRead, 0);
        Page<CfMsg> page = apiCfMsgService.page(new Page<>(request.getPageNum(), request.getPageSize()), query);
        List<MsgListVO> voList = page.getRecords()
                .stream()
                .map(ApiCfMsgConvert::po2ListVo)
                .collect(Collectors.toList());
        return new PageVO<>((int)page.getCurrent(), (int)page.getSize(), (int)page.getPages(), page.getTotal(), voList);
    }

    public Void frontReadMsgList(IdsRequest request) {
        if (CollectionUtil.isEmpty(request.getIds())) {
            return null;
        }
        for (Integer id : request.getIds()) {
            CfMsg tMsg = new CfMsg();
            tMsg.setId(Long.valueOf(id));
            tMsg.setHasRead(1);
            tMsg.setUpdateTime(LocalDateTime.now());
            apiCfMsgService.updateById(tMsg);
        }
        return null;
    }

}
