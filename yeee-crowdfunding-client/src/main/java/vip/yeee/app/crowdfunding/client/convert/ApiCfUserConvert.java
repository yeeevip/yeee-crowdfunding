package vip.yeee.app.crowdfunding.client.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfUser;
import vip.yeee.app.crowdfunding.client.model.vo.CfUserInfoVO;
import vip.yeee.app.crowdfunding.client.model.vo.UserVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/30 20:18
 */
@Component
public class ApiCfUserConvert {

    public UserVO user2VO(CfUser user) {
        if (user == null) {
            return null;
        }
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickName(user.getNickName());
        vo.setEmail(user.getEmail());
        vo.setSex(user.getSex());
        vo.setRealName(user.getRealName());
        vo.setIdNumber(user.getIdNumber());
        vo.setDateOfBirth(user.getDateOfBirth());
        vo.setMobile(user.getMobile());
        vo.setCity(user.getCity());
        vo.setDateOfRegistration(user.getDateOfRegistration());
        return vo;
    }

    public CfUserInfoVO user2InfoVO(CfUser user) {
        if (user == null) {
            return null;
        }
        CfUserInfoVO vo = new CfUserInfoVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickName(user.getNickName());
        vo.setEmail(user.getEmail());
        vo.setSex(user.getSex());
        vo.setRealName(user.getRealName());
        vo.setIdNumber(user.getIdNumber());
        vo.setDateOfBirth(user.getDateOfBirth());
        vo.setMobile(user.getMobile());
        vo.setCity(user.getCity());
        vo.setDateOfRegistration(user.getDateOfRegistration());
        return vo;
    }

    public CfUser vo2Entity(UserVO userVO) {
        if (userVO == null) {
            return null;
        }
        CfUser entity = new CfUser();
        entity.setId(userVO.getId());
        entity.setUsername(userVO.getUsername());
        entity.setNickName(userVO.getNickName());
        entity.setEmail(userVO.getEmail());
        entity.setSex(userVO.getSex());
        entity.setRealName(userVO.getRealName());
        entity.setIdNumber(userVO.getIdNumber());
        entity.setDateOfBirth(userVO.getDateOfBirth());
        entity.setMobile(userVO.getMobile());
        entity.setCity(userVO.getCity());
        entity.setDateOfRegistration(userVO.getDateOfRegistration());
        return entity;
    }

}
