package vip.yeee.app.crowdfunding.client.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfReceiveInformation;
import vip.yeee.app.crowdfunding.client.domain.mysql.mapper.ApiCfReceiveInformationMapper;

import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/7 23:08
 */
@Service
@RequiredArgsConstructor
public class ApiCfReceiveInformationService extends ServiceImpl<ApiCfReceiveInformationMapper, CfReceiveInformation> {

    public CfReceiveInformation getDefaultReceiveInfoByUserId(Integer currentUserId) {
        LambdaQueryWrapper<CfReceiveInformation> query = Wrappers.lambdaQuery();
        query.eq(CfReceiveInformation::getSetDefault, 1);
        query.eq(CfReceiveInformation::getUserId, currentUserId);
        return this.getOne(query);
    }

    public List<CfReceiveInformation> getListByUserId(Integer curUserId) {
        LambdaQueryWrapper<CfReceiveInformation> query = Wrappers.lambdaQuery();
        query.eq(CfReceiveInformation::getUserId, curUserId);
        return this.list(query);
    }

    public List<CfReceiveInformation> getListByUserIdAndDefault(Integer curUserId, Integer defaultSet) {
        LambdaQueryWrapper<CfReceiveInformation> query = Wrappers.lambdaQuery();
        query.eq(CfReceiveInformation::getSetDefault, 1);
        query.eq(CfReceiveInformation::getUserId, curUserId);
        return this.list(query);
    }
}
