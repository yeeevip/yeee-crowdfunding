package vip.yeee.app.crowdfunding.client.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfUser;
import vip.yeee.app.crowdfunding.client.domain.mysql.mapper.ApiCfUserMapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 16:19
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ApiCfUserService extends ServiceImpl<ApiCfUserMapper, CfUser> {

    public CfUser getByUsername(String username) {
        LambdaQueryWrapper<CfUser> query = Wrappers.lambdaQuery();
        query.eq(CfUser::getUsername, username);
        return this.getOne(query);
    }
}
