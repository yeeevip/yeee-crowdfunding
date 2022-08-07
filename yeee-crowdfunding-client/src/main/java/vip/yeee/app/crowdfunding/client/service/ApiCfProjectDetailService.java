package vip.yeee.app.crowdfunding.client.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfProjectDetail;
import vip.yeee.app.crowdfunding.client.domain.mysql.mapper.ApiCfProjectDetailMapper;

import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/28 17:46
 */
@Service
public class ApiCfProjectDetailService extends ServiceImpl<ApiCfProjectDetailMapper, CfProjectDetail> {

    public List<CfProjectDetail> getDetailByProjectId(Integer projectId) {
        LambdaQueryWrapper<CfProjectDetail> query = Wrappers.lambdaQuery();
        query.eq(CfProjectDetail::getProjectId, projectId);
        return list(query);
    }

}
