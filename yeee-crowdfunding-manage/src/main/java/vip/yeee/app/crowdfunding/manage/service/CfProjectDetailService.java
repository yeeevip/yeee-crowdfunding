package vip.yeee.app.crowdfunding.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfProjectDetail;
import vip.yeee.app.crowdfunding.manage.domain.mysql.mapper.CfProjectDetailMapper;

import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/28 17:46
 */
@Service
public class CfProjectDetailService extends ServiceImpl<CfProjectDetailMapper, CfProjectDetail> {

    public List<CfProjectDetail> getDetailByProjectId(Integer projectId) {
        LambdaQueryWrapper<CfProjectDetail> query = Wrappers.lambdaQuery();
        query.eq(CfProjectDetail::getProjectId, projectId);
        return list(query);
    }
}
