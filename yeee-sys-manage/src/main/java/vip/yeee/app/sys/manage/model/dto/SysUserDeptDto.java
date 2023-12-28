package vip.yeee.app.sys.manage.model.dto;

import lombok.Data;
import vip.yeee.app.common.domain.mysql.entity.SysUserDept;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/12/28 15:45
 */
@Data
public class SysUserDeptDto extends SysUserDept {

    private String deptCode;
}
