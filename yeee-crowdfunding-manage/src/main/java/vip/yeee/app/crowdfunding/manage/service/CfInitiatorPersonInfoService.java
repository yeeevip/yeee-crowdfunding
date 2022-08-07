package vip.yeee.app.crowdfunding.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfInitiatorPersonInfo;
import vip.yeee.app.crowdfunding.manage.domain.mysql.mapper.CfInitiatorPersonInfoMapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/28 18:15
 */
@Service
public class CfInitiatorPersonInfoService extends ServiceImpl<CfInitiatorPersonInfoMapper, CfInitiatorPersonInfo> {

    public CfInitiatorPersonInfo getInitiatorPersonInfo(Integer projectId) {
        LambdaQueryWrapper<CfInitiatorPersonInfo> query = Wrappers.lambdaQuery();
        query.eq(CfInitiatorPersonInfo::getProjectId, projectId);
        return this.getOne(query);
    }
}
