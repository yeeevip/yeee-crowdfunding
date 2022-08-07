package vip.yeee.app.crowdfunding.client.domain.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import vip.yeee.app.common.domain.mysql.entity.CfProject;

import java.util.List;
import java.util.Map;

/**
 * create by yeah.一页 2022/04/29 21:31:09
 */
public interface ApiCfProjectMapper extends BaseMapper<CfProject> {

    List<CfProject> getOrderLimitList(@Param("project") CfProject project, @Param("params") Map<String, Object> params);

    List<CfProject> getProjectList(CfProject query);
}




