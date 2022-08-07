package vip.yeee.app.crowdfunding.manage.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfOrder;
import vip.yeee.app.crowdfunding.manage.domain.mysql.mapper.CfOrderMapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/30 19:34
 */
@RequiredArgsConstructor
@Service
public class CfOrderService extends ServiceImpl<CfOrderMapper, CfOrder> {

}
