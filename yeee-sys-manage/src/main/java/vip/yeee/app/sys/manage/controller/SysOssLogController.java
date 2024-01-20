package vip.yeee.app.sys.manage.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.yeee.app.sys.manage.biz.SysOssLogBiz;

import vip.yeee.app.sys.manage.model.vo.SysOssLogListVo;
import vip.yeee.memo.base.model.rest.CommonResult;
import vip.yeee.memo.base.model.vo.PageVO;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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

    @ApiOperation("列表")
    @GetMapping(value = "/page")
    public CommonResult<PageVO<SysOssLogListVo>> sysOssLogPageList(String query) {
        return CommonResult.success(sysOssLogBiz.sysOssLogPageList(query));
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

}
