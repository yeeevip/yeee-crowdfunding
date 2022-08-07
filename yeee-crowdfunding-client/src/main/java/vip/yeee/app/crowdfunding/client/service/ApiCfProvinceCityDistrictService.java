package vip.yeee.app.crowdfunding.client.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vip.yeee.app.common.domain.mysql.entity.CfProvinceCityDistrict;
import vip.yeee.app.crowdfunding.client.domain.mysql.mapper.ApiCfProvinceCityDistrictMapper;

import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/1 13:15
 */
@RequiredArgsConstructor
@Service
public class ApiCfProvinceCityDistrictService extends ServiceImpl<ApiCfProvinceCityDistrictMapper, CfProvinceCityDistrict> {

    public List<CfProvinceCityDistrict> getListByPid(Integer pid) {
        LambdaQueryWrapper<CfProvinceCityDistrict> query = Wrappers.lambdaQuery();
        query.eq(CfProvinceCityDistrict::getPid, pid);
        return this.list(query);
    }
}
