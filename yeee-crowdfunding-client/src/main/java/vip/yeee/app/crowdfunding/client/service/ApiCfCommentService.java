package vip.yeee.app.crowdfunding.client.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfComment;
import vip.yeee.app.crowdfunding.client.domain.mysql.mapper.ApiCfCommentMapper;
import vip.yeee.app.crowdfunding.client.model.dto.CommentDto;
import vip.yeee.app.crowdfunding.client.model.vo.CommentVO;

import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/1 10:15
 */
@RequiredArgsConstructor
@Service
public class ApiCfCommentService extends ServiceImpl<ApiCfCommentMapper, CfComment> {

    public List<CfComment> getCommentListByProjectId(Integer projectId) {
        LambdaQueryWrapper<CfComment> query = Wrappers.lambdaQuery();
        query.eq(CfComment::getProject, projectId);
        return this.list(query);
    }

    public List<CommentVO> getProjectCommentList(CommentDto query) {
        return baseMapper.getProjectCommentList(query);
    }
}
