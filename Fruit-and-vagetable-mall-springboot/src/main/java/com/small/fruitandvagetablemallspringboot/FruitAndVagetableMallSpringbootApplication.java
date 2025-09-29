package com.small.fruitandvagetablemallspringboot;

import com.small.fruitandvagetablemallspringboot.pojo.FileProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//开启事务控制
@EnableTransactionManagement
//配置文件上传和下载
@EnableConfigurationProperties({
        FileProperty.class
})
public class FruitAndVagetableMallSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FruitAndVagetableMallSpringbootApplication.class, args);
    }

}
