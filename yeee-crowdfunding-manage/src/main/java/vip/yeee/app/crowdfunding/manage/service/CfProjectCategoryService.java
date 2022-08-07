package vip.yeee.app.crowdfunding.manage.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfProjectCategory;
import vip.yeee.app.crowdfunding.manage.domain.mysql.mapper.CfProjectCategoryMapper;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/8 16:41
 */
@RequiredArgsConstructor
@Service
public class CfProjectCategoryService extends ServiceImpl<CfProjectCategoryMapper, CfProjectCategory> {

}
