package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("t_cf_initiator_person_info")
public class CfInitiatorPersonInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer projectId;

    private String name;

    private String idNumber;

    private String phone;

    private String address;

    private String idPicFace;

    private String idPicInverse;
}