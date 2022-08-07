package vip.yeee.app.crowdfunding.client.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfInitiatorCompanyInfo;
import vip.yeee.app.common.domain.mysql.entity.CfInitiatorPersonInfo;
import vip.yeee.app.crowdfunding.client.model.vo.InitiatorCompanyInfoVO;
import vip.yeee.app.crowdfunding.client.model.vo.InitiatorPersonInfoVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 22:25
 */
@Mapper(componentModel = "spring")
public interface ApiCfInitiatorInfoVOConvert {

    CfInitiatorPersonInfo personInfoVO2Entity(InitiatorPersonInfoVO personInfoVO);

    InitiatorPersonInfoVO entity2PersonInfoVO(CfInitiatorPersonInfo initiatorPersonInfo);

    CfInitiatorCompanyInfo companyInfoVO2Entity(InitiatorCompanyInfoVO companyInfoVO);

    InitiatorCompanyInfoVO entity2CompanyInfoVO(CfInitiatorCompanyInfo initiatorCompanyInfo);

}
