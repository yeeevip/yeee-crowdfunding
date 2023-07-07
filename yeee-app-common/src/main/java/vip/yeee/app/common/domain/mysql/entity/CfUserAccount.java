package vip.yeee.app.common.domain.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yeeee
 * @since 2023-02-07
 */
@Data
@Accessors(chain = true)
@TableName("t_cf_user_account")
public class CfUserAccount {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    /**
     * 余额
     */
    private Long balance;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
