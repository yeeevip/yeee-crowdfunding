package vip.yeee.app.crowdfunding.client.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfProject;
import vip.yeee.app.crowdfunding.client.domain.mysql.mapper.ApiCfProjectMapper;

import java.util.List;
import java.util.Map;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 21:38
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ApiCfProjectService extends ServiceImpl<ApiCfProjectMapper, CfProject> {

    public List<CfProject> getOrderLimitList(CfProject project, Map<String, Object> params) {
        return baseMapper.getOrderLimitList(project, params);
    }

    public List<CfProject> getProjectList(CfProject query) {
        return baseMapper.getProjectList(query);
    }
}
