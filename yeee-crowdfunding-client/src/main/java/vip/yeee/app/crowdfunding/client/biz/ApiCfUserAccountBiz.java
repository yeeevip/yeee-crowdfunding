package vip.yeee.app.crowdfunding.client.biz;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfUserAccount;
import vip.yeee.app.crowdfunding.client.model.vo.UserAccountFillRequest;
import vip.yeee.app.crowdfunding.client.model.vo.UserAccountVO;
import vip.yeee.app.crowdfunding.client.service.ApiCfUserAccountService;
import vip.yeee.app.crowdfunding.client.utils.BusinessUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/6/29 21:57
 */
@Component
public class ApiCfUserAccountBiz {

    @Resource
    private ApiCfUserAccountService apiCfUserAccountService;

    public UserAccountVO frontMyAccount() {
        Integer userId = BusinessUtils.getCurUserId();
        CfUserAccount account = apiCfUserAccountService.getUserAccountByUserId(userId);
        if (account == null) {
            account = new CfUserAccount();
            account.setUserId(userId);
            account.setCreateTime(LocalDateTime.now());
            apiCfUserAccountService.save(account);
        }
        UserAccountVO accountVO = new UserAccountVO();
        accountVO.setId(account.getId().toString());
        accountVO.setBalance(account.getBalance());
        accountVO.setCreateTime(account.getCreateTime());
        accountVO.setUpdateTime(account.getUpdateTime());
        return accountVO;
    }

    public Void frontFillAccount(UserAccountFillRequest request) {
        Integer userId = BusinessUtils.getCurUserId();
        CfUserAccount account = apiCfUserAccountService.getUserAccountByUserId(userId);
        if (account == null) {
            account = new CfUserAccount();
            account.setUserId(userId);
            account.setCreateTime(LocalDateTime.now());
            apiCfUserAccountService.save(account);
        }
        apiCfUserAccountService.increaseUserBalance(account, request.getBalance());
        return null;
    }

}
