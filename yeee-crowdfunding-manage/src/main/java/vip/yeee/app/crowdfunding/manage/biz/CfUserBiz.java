package vip.yeee.app.crowdfunding.manage.biz;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfUser;
import vip.yeee.app.crowdfunding.manage.convert.CfUserConvert;
import vip.yeee.app.crowdfunding.manage.model.request.CfUserEditRequest;
import vip.yeee.app.crowdfunding.manage.model.request.IdsRequest;
import vip.yeee.app.crowdfunding.manage.model.vo.CfUserInfoVO;
import vip.yeee.app.crowdfunding.manage.model.vo.UserVO;
import vip.yeee.app.crowdfunding.manage.service.CfUserService;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.base.model.vo.PageVO;
import vip.yeee.memo.base.mybatisplus.warpper.MyPageWrapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/2/6 20:20
 */
@Component
public class CfUserBiz {

    @Resource
    private CfUserService cfUserService;
    @Resource
    private CfUserConvert cfUserConvert;

    public PageVO<UserVO> cfUserPageList(String query) {
        MyPageWrapper<CfUser> wrapper = new MyPageWrapper<>(query);
        IPage<CfUser> page = cfUserService.page(wrapper.getPage(), wrapper.getQueryWrapper());
        List<UserVO> userVOList = Optional
                .ofNullable(page.getRecords())
                .orElseGet(Lists::newArrayList)
                .stream()
                .map(cfUserConvert::user2VO)
                .collect(Collectors.toList());
        return new PageVO<>((int)page.getCurrent(), (int)page.getSize(), (int)page.getPages(), page.getTotal(), userVOList);
    }

    public Boolean cfUserExist(String query) {
        MyPageWrapper<CfUser> pageWrapper = new MyPageWrapper<>(query);
        return cfUserService.count(pageWrapper.getQueryWrapper()) > 0;
    }

    public Void addCfUser(CfUserEditRequest request) {
        CfUser save = new CfUser();
        BeanUtils.copyProperties(request, save);
        save.setPassword(SecureUtil.md5(StrUtil.emptyToDefault(request.getPassword(), "111111")));
        cfUserService.save(save);
        return null;
    }

    public Void editCfUser(CfUserEditRequest request) {
        CfUser user = cfUserService.getById(request.getId());
        if (user == null) {
            throw new BizException("用户不存在");
        }
        CfUser upd = new CfUser();
        BeanUtils.copyProperties(request, upd);
        upd.setId(request.getId());
        if (StrUtil.isNotBlank(request.getPassword())) {
            String encodePassword = SecureUtil.md5(request.getPassword());
            if (!request.getPassword().equals(user.getPassword())) {
                upd.setPassword(encodePassword);
            }
        }
        cfUserService.updateById(upd);
        return null;
    }

    public CfUserInfoVO cfUserInfo(CfUserEditRequest request) {
        CfUser user = cfUserService.getById(request.getId());
        if (user == null) {
            throw new BizException("用户不存在");
        }
        return cfUserConvert.user2InfoVO(user);
    }

    public Void delCfUser(IdsRequest request) {
        if (CollectionUtil.isEmpty(request.getIds())) {
            return null;
        }
        cfUserService.removeByIds(request.getIds());
        return null;
    }
}
