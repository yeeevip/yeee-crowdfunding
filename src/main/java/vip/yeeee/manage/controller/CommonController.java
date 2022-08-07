package vip.yeeee.manage.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.yeee.memo.integrate.common.model.rest.CommonResult;
import vip.yeee.memo.integrate.common.security.annotation.AnonymousAccess;

import java.io.*;
import java.util.List;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/5/1 12:58
 */
@Slf4j
@Api(tags = "通用接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("general")
public class CommonController {

    @Value("${local.upload.location}")
    private String uploadPath;


    @ApiOperation("上传")
    @AnonymousAccess
    @PostMapping("upload")
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
