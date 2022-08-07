package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("t_cf_initiator_company_info")
public class CfInitiatorCompanyInfo {

    private Integer id;

    private Integer projectId;

    private String firmName;

    private String businessNumber;

    private String slanderName;

    private String address;

    private String contactName;

    private String contactPhone;

    private String licensePic;

    private String registeredNumPic;

    private String taxPig;
}