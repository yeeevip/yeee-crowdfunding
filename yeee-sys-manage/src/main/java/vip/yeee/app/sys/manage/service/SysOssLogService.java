package vip.yeee.app.sys.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import vip.yeee.app.common.domain.mysql.entity.SysOssLog;
import vip.yeee.app.sys.manage.domain.mysql.mapper.SysOssLogMapper;
import vip.yeee.memo.base.mybatisplus.warpper.MyPageWrapper;

/**
* create by yeee.一页 2023/06/13 10:43:41
*/
@Service
public class SysOssLogService extends ServiceImpl<SysOssLogMapper, SysOssLog> {

    public IPage<SysOssLog> sysOssLogPageList(String request) {
        MyPageWrapper<SysOssLog> pageWrapper = new MyPageWrapper<>(request);
        QueryWrapper<SysOssLog> queryWrapper = pageWrapper.getQueryWrapper();
        return this.page((Page<SysOssLog>)pageWrapper.getPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public void sysOssLogAdd(SysOssLog sysOssLog) {
        this.save(sysOssLog);
    }

}
