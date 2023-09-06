package vip.yeee.app.crowdfunding.client.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfProjectProgress;
import vip.yeee.app.crowdfunding.client.domain.mysql.mapper.ApiCfProjectProgressMapper;

import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/29 19:00
 */
@Service
public class ApiCfProjectProgressService extends ServiceImpl<ApiCfProjectProgressMapper, CfProjectProgress> {

    public List<CfProjectProgress> getListByProjectId(Integer projectId) {
        LambdaQueryWrapper<CfProjectProgress> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(CfProjectProgress::getId);
        queryWrapper.eq(CfProjectProgress::getProjectId, projectId);
        return this.list(queryWrapper);
    }
}
