package vip.yeee.app.sys.manage.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vip.yeee.app.common.domain.mysql.entity.SysUserRole;

/**
 * description......
 *
 * @author yeeee
 * @since 2023/12/28 15:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserRoleDto extends SysUserRole {

    private String roleCode;
}
