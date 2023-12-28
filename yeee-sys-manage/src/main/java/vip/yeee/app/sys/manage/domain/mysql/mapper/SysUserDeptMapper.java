package vip.yeee.app.sys.manage.domain.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.yeee.app.common.domain.mysql.entity.SysUserDept;
import vip.yeee.app.sys.manage.model.dto.SysUserDeptDto;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author yeeee
 * @since 2022-05-29
 */
public interface SysUserDeptMapper extends BaseMapper<SysUserDept> {

    List<SysUserDeptDto> getList(SysUserDept sysUserDept);
}
