package vip.yeee.app.crowdfunding.client.convert;

import org.springframework.stereotype.Component;
import vip.yeee.app.common.domain.mysql.entity.CfInitiatorCompanyInfo;
import vip.yeee.app.common.domain.mysql.entity.CfInitiatorPersonInfo;
import vip.yeee.app.crowdfunding.client.model.vo.InitiatorCompanyInfoVO;
import vip.yeee.app.crowdfunding.client.model.vo.InitiatorPersonInfoVO;

/**
 * description......
 *
 * @author https://www.yeee.vip
 * @since 2022/4/29 22:25
 */
@Component
public class ApiCfInitiatorInfoVOConvert {

    public CfInitiatorPersonInfo personInfoVO2Entity(InitiatorPersonInfoVO personInfoVO) {
        if (personInfoVO == null) {
            return null;
        }
        CfInitiatorPersonInfo entity = new CfInitiatorPersonInfo();
        entity.setName(personInfoVO.getName());
        entity.setIdNumber(personInfoVO.getIdNumber());
        entity.setPhone(personInfoVO.getPhone());
        entity.setAddress(personInfoVO.getAddress());
        entity.setIdPicFace(personInfoVO.getIdPicFace());
        entity.setIdPicInverse(personInfoVO.getIdPicInverse());
        return entity;
    }

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

    public CfInitiatorCompanyInfo companyInfoVO2Entity(InitiatorCompanyInfoVO companyInfoVO) {
        if (companyInfoVO == null) {
            return null;
        }
        CfInitiatorCompanyInfo entity = new CfInitiatorCompanyInfo();
        entity.setFirmName(companyInfoVO.getFirmName());
        entity.setBusinessNumber(companyInfoVO.getBusinessNumber());
        entity.setSlanderName(companyInfoVO.getSlanderName());
        entity.setAddress(companyInfoVO.getAddress());
        entity.setContactName(companyInfoVO.getContactName());
        entity.setContactPhone(companyInfoVO.getContactPhone());
        entity.setLicensePic(companyInfoVO.getLicensePic());
        entity.setRegisteredNumPic(companyInfoVO.getRegisteredNumPic());
        entity.setTaxPig(companyInfoVO.getTaxPig());
        return entity;
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
