package vip.yeee.app.sys.manage.domain.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.yeee.app.common.domain.mysql.entity.SysOssLog;

import java.util.List;

/**
 * create by yeee.一页 2023/06/13 10:43:41
 */
public interface SysOssLogMapper extends BaseMapper<SysOssLog> {

    List<SysOssLog> getList(SysOssLog sysOssLog);

    SysOssLog getOne(SysOssLog sysOssLog);

    int batchInsert(List<SysOssLog> sysOssLogList);

}
