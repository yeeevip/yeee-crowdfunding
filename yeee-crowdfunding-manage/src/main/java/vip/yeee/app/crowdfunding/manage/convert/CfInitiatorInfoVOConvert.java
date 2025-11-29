package vip.yeee.app.crowdfunding.manage.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfInitiatorCompanyInfo;
import vip.yeee.app.common.domain.mysql.entity.CfInitiatorPersonInfo;
import vip.yeee.app.crowdfunding.manage.model.vo.InitiatorCompanyInfoVO;
import vip.yeee.app.crowdfunding.manage.model.vo.InitiatorPersonInfoVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/29 22:25
 */
@Component
public class CfInitiatorInfoVOConvert {

    public InitiatorPersonInfoVO entity2PersonInfoVO(CfInitiatorPersonInfo initiatorPersonInfo) {
        if (initiatorPersonInfo == null) {
            return null;
        }
        InitiatorPersonInfoVO vo = new InitiatorPersonInfoVO();
        vo.setName(initiatorPersonInfo.getName());
        vo.setIdNumber(initiatorPersonInfo.getIdNumber());
        vo.setPhone(initiatorPersonInfo.getPhone());
        vo.setAddress(initiatorPersonInfo.getAddress());
        vo.setIdPicFace(initiatorPersonInfo.getIdPicFace());
        vo.setIdPicInverse(initiatorPersonInfo.getIdPicInverse());
        return vo;
    }

    public InitiatorCompanyInfoVO entity2CompanyInfoVO(CfInitiatorCompanyInfo initiatorCompanyInfo) {
        if (initiatorCompanyInfo == null) {
            return null;
        }
        InitiatorCompanyInfoVO vo = new InitiatorCompanyInfoVO();
        vo.setFirmName(initiatorCompanyInfo.getFirmName());
        vo.setBusinessNumber(initiatorCompanyInfo.getBusinessNumber());
        vo.setSlanderName(initiatorCompanyInfo.getSlanderName());
        vo.setAddress(initiatorCompanyInfo.getAddress());
        vo.setContactName(initiatorCompanyInfo.getContactName());
        vo.setContactPhone(initiatorCompanyInfo.getContactPhone());
        vo.setLicensePic(initiatorCompanyInfo.getLicensePic());
        vo.setRegisteredNumPic(initiatorCompanyInfo.getRegisteredNumPic());
        vo.setTaxPig(initiatorCompanyInfo.getTaxPig());
        return vo;
    }

}
