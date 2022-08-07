package vip.yeee.app.crowdfunding.manage.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfComment;
import vip.yeee.app.crowdfunding.manage.domain.mysql.mapper.CfCommentMapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/1 10:15
 */
@RequiredArgsConstructor
@Service
public class CfCommentService extends ServiceImpl<CfCommentMapper, CfComment> {

}
