package vip.yeee.app.crowdfunding.client.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfProjectCategory;
import vip.yeee.app.crowdfunding.client.domain.mysql.mapper.ApiCfProjectCategoryMapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/8 16:41
 */
@RequiredArgsConstructor
@Service
public class ApiCfProjectCategoryService extends ServiceImpl<ApiCfProjectCategoryMapper, CfProjectCategory> {

}
