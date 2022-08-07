package vip.yeeee.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("vip.yeeee.manage.mapper")
public class YeeeeManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(YeeeeManageApplication.class, args);
    }

}
