package vip.yeee.app.crowdfunding.client.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfProjectRepay;
import vip.yeee.app.crowdfunding.client.domain.mysql.mapper.ApiCfProjectRepayMapper;

import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/28 18:10
 */
@Service
public class ApiCfProjectRepayService extends ServiceImpl<ApiCfProjectRepayMapper, CfProjectRepay> {

    public List<CfProjectRepay> getRepayByProjectId(Integer projectId) {
        LambdaQueryWrapper<CfProjectRepay> query = Wrappers.lambdaQuery();
        query.eq(CfProjectRepay::getProjectId, projectId);
        return list(query);
    }
}
