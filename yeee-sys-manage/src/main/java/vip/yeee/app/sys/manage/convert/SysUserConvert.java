package vip.yeee.app.sys.manage.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.sys.manage.model.vo.SysUserEditVO;
import vip.yeee.app.sys.manage.model.vo.UserVO;
import vip.yeee.app.sys.manage.model.vo.SysUserInfoVO;
import vip.yeee.app.common.domain.mysql.entity.SysUser;
import vip.yeee.memo.base.websecurityoauth2.model.AuthedUser;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/30 20:18
 */
@Component
public class SysUserConvert {

    public UserVO sysUser2VO(SysUser sysUser) {
        if (sysUser == null) {
            return null;
        }
        UserVO vo = new UserVO();
        vo.setId(sysUser.getId());
        vo.setUsername(sysUser.getUsername());
        vo.setNickName(sysUser.getNickName());
        vo.setEmail(sysUser.getEmail());
        vo.setPhone(sysUser.getPhone());
        vo.setCreateTime(sysUser.getCreateTime());
        return vo;
    }

    public UserVO securityUser2VO(AuthedUser securityUser) {
        if (securityUser == null) {
            return null;
        }
        UserVO vo = new UserVO();
        if (securityUser.getId() != null) {
            try {
                vo.setId(Integer.valueOf(securityUser.getId()));
            } catch (NumberFormatException e) {
                // 如果 ID 不是数字，则忽略
            }
        }
        vo.setUsername(securityUser.getUsername());
        // AuthedUser 只包含基本字段，其他字段保持默认值
        return vo;
    }

    public SysUser editVO2Entity(SysUserEditVO editVO) {
        if (editVO == null) {
            return null;
        }
        SysUser entity = new SysUser();
        entity.setId(editVO.getId());
        entity.setUsername(editVO.getUsername());
        entity.setNickName(editVO.getNickName());
        entity.setPhone(editVO.getPhone());
        entity.setEmail(editVO.getEmail());
        return entity;
    }

    public SysUserInfoVO entity2InfoVO(SysUser sysUser) {
        if (sysUser == null) {
            return null;
        }
        SysUserInfoVO vo = new SysUserInfoVO();
        vo.setId(sysUser.getId());
        vo.setUsername(sysUser.getUsername());
        vo.setNickName(sysUser.getNickName());
        vo.setPhone(sysUser.getPhone());
        vo.setEmail(sysUser.getEmail());
        return vo;
    }

}
