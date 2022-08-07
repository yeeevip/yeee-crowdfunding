package vip.yeee.app.crowdfunding.manage.convert;

import org.mapstruct.Mapper;
import vip.yeee.app.common.domain.mysql.entity.CfInitiatorCompanyInfo;
import vip.yeee.app.common.domain.mysql.entity.CfInitiatorPersonInfo;
import vip.yeee.app.crowdfunding.manage.model.vo.InitiatorCompanyInfoVO;
import vip.yeee.app.crowdfunding.manage.model.vo.InitiatorPersonInfoVO;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 22:25
 */
@Mapper(componentModel = "spring")
public interface CfInitiatorInfoVOConvert {

    InitiatorPersonInfoVO entity2PersonInfoVO(CfInitiatorPersonInfo initiatorPersonInfo);

    InitiatorCompanyInfoVO entity2CompanyInfoVO(CfInitiatorCompanyInfo initiatorCompanyInfo);

}
