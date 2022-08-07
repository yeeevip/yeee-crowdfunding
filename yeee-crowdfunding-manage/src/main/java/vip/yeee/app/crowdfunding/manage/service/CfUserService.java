package vip.yeee.app.crowdfunding.manage.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfUser;
import vip.yeee.app.crowdfunding.manage.domain.mysql.mapper.CfUserMapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 16:19
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CfUserService extends ServiceImpl<CfUserMapper, CfUser> {

}
