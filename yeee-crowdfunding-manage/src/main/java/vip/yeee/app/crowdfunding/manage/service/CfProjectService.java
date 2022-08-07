package vip.yeee.app.crowdfunding.manage.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfProject;
import vip.yeee.app.crowdfunding.manage.domain.mysql.mapper.CfProjectMapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 21:38
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CfProjectService extends ServiceImpl<CfProjectMapper, CfProject> {

}
