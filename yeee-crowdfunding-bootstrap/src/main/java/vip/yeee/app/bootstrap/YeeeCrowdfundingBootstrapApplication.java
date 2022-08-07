package vip.yeee.app.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan({"vip.yeee.app.sys.manage.domain.mysql.mapper"
        , "vip.yeee.app.crowdfunding.manage.domain.mysql.mapper"
        , "vip.yeee.app.crowdfunding.client.domain.mysql.mapper"})
@ComponentScan({"vip.yeee.app"})
public class YeeeCrowdfundingBootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(YeeeCrowdfundingBootstrapApplication.class, args);
    }

}
