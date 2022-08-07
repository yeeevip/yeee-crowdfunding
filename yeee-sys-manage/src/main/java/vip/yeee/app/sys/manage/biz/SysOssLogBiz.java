package vip.yeee.app.sys.manage.biz;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import vip.yeee.app.sys.manage.model.vo.SysOssLogListVo;
import vip.yeee.app.sys.manage.service.SysOssLogService;
import vip.yeee.app.common.domain.mysql.entity.SysOssLog;
import vip.yeee.memo.base.model.vo.PageVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* create by yeee.一页 2023/06/13 10:43:41
*/
@Component
public class SysOssLogBiz {

    @Resource
    private SysOssLogService sysOssLogService;

    public PageVO<SysOssLogListVo> sysOssLogPageList(String query) {
        IPage<SysOssLog> page = sysOssLogService.sysOssLogPageList(query);
        PageVO<SysOssLogListVo> pageVO = new PageVO<>((int) page.getCurrent(), (int) page.getSize());
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return pageVO;
        }
        List<SysOssLogListVo> voList = page.getRecords()
                .stream()
                .map(po -> {
                    SysOssLogListVo vo = new SysOssLogListVo();
                    BeanUtils.copyProperties(po, vo);
                    vo.setId(po.getId().toString());
                    return vo;
                })
                .collect(Collectors.toList());
        pageVO.setPages((int) page.getPages());
        pageVO.setTotal(page.getTotal());
        pageVO.setResult(voList);
        return pageVO;
    }

}
