package vip.yeee.app.sys.manage.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.yeee.app.sys.manage.biz.SysOssLogBiz;
import vip.yeee.app.sys.manage.model.request.SysOssLogAddRequest;
import vip.yeee.app.sys.manage.model.vo.SysOssLogListVo;
import vip.yeee.memo.base.model.exception.BizException;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;
import vip.yeee.memo.common.oss.kit.OssKit;
import vip.yeee.memo.common.oss.model.StsAuthInfo;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
* create by yeee.一页 2023/06/13 10:43:41
*/
@Slf4j
@Api(tags = "系统后台-OSS上传")
@RequestMapping("/manage/sys-ossLog")
@RestController
public class SysOssLogController {

    @Resource
    private SysOssLogBiz sysOssLogBiz;

    @Value("${yeee.oss.local.upload.base-dir}")
    private String uploadPath;

    @Autowired
    private Map<String, OssKit> ossKitMap;

    @ApiOperation("列表")
    @GetMapping(value = "/page")
    public CommonResult<PageVO<SysOssLogListVo>> sysOssLogPageList(String query) {
        return CommonResult.success(sysOssLogBiz.sysOssLogPageList(query));
    }

    @ApiOperation("创建")
    @PostMapping(value = "/add")
    public CommonResult<Void> sysOssLogAdd(@RequestBody @Validated SysOssLogAddRequest request) {
        return CommonResult.success(sysOssLogBiz.sysOssLogAdd(request));
    }

    @ApiOperation("本地上传")
    @PostMapping("/upload/local")
    public CommonResult<String> upload(@RequestPart List<MultipartFile> file, @RequestParam String path) throws FileNotFoundException {
        List<String> paths = Lists.newArrayList();
        file.forEach(f -> {
            String dirPath;
            try (OutputStream out = FileUtil.getOutputStream(FileUtil.file(uploadPath + (dirPath = ("upload/" + path + "/" + RandomUtil.randomNumbers(5) + f.getOriginalFilename()))))) {
                IoUtil.copy(f.getInputStream(), out);
                paths.add("/" + dirPath.replace("\\", "/"));
            } catch (IOException e) {
                log.error("upload err", e);
            }
        });
        return CommonResult.success(String.join(";", paths));
    }

    @ApiOperation("获取上传凭证")
    @PostMapping("/upload/sts")
    public CommonResult<StsAuthInfo> uploadSts(@RequestParam String channel, String fileKey) throws Exception {
        Map<String, Object> params = Maps.newHashMap();
        params.put("fileKey", fileKey);
        OssKit ossKit = ossKitMap.get(channel + "OssKit");
        if (ossKit == null) {
            throw new BizException("渠道不存在");
        }
        StsAuthInfo authInfo = ossKit.stsAuthorize(params);
        return CommonResult.success(authInfo);
    }
}
