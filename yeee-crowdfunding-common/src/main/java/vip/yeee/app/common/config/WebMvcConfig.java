package vip.yeee.app.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * description......
 *
 * @author yeeee
 * @since 2022/4/29 17:01
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${yeee.oss.local.upload.base-dir}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + uploadPath + "upload/");
    }
}
