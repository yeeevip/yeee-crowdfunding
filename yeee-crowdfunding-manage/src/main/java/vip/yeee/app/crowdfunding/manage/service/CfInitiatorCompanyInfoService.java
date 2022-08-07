package vip.yeee.app.crowdfunding.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfInitiatorCompanyInfo;
import vip.yeee.app.crowdfunding.manage.domain.mysql.mapper.CfInitiatorCompanyInfoMapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/28 18:47
 */
@Service
public class CfInitiatorCompanyInfoService extends ServiceImpl<CfInitiatorCompanyInfoMapper, CfInitiatorCompanyInfo> {

    public CfInitiatorCompanyInfo getInitiatorCompanyInfo(Integer projectId) {
        LambdaQueryWrapper<CfInitiatorCompanyInfo> query = Wrappers.lambdaQuery();
        query.eq(CfInitiatorCompanyInfo::getProjectId, projectId);
        return this.getOne(query);
    }
}
