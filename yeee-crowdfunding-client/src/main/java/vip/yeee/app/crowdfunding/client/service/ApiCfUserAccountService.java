package vip.yeee.app.crowdfunding.client.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfUserAccount;
import vip.yeee.app.crowdfunding.client.domain.mysql.mapper.ApiCfUserAccountMapper;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yeeee
 * @since 2023-02-07
 */
@Service
public class ApiCfUserAccountService extends ServiceImpl<ApiCfUserAccountMapper, CfUserAccount> {

    public CfUserAccount getUserAccountByUserId(Integer curUserId) {
        LambdaQueryWrapper<CfUserAccount> query = Wrappers.lambdaQuery();
        query.eq(CfUserAccount::getUserId, curUserId);
        return this.getOne(query);
    }

    public void increaseUserBalance(CfUserAccount account, long balance) {
        LambdaUpdateWrapper<CfUserAccount> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CfUserAccount::getId, account.getId());
        updateWrapper.eq(CfUserAccount::getBalance, account.getBalance());
        updateWrapper.apply("balance + {0} >= 0", balance);
        updateWrapper.set(CfUserAccount::getBalance, balance + account.getBalance());
        updateWrapper.set(CfUserAccount::getUpdateTime, LocalDateTime.now());
        this.update(updateWrapper);
    }
}
