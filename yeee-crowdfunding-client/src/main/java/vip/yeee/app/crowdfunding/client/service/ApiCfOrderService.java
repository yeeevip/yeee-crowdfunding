package vip.yeee.app.crowdfunding.client.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfOrder;
import vip.yeee.app.crowdfunding.client.domain.mysql.mapper.ApiCfOrderMapper;

import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 19:34
 */
@RequiredArgsConstructor
@Service
public class ApiCfOrderService extends ServiceImpl<ApiCfOrderMapper, CfOrder> {

    public List<CfOrder> getListByUserId(Integer curUserId) {
        LambdaQueryWrapper<CfOrder> query = Wrappers.lambdaQuery();
        query.eq(CfOrder::getUserId, curUserId);
        return this.list(query);
    }

    public List<CfOrder> getListBySellerUserId(Integer curUserId) {
        LambdaQueryWrapper<CfOrder> query = Wrappers.lambdaQuery();
        query.eq(CfOrder::getUserSeller, curUserId);
        return this.list(query);
    }
}
