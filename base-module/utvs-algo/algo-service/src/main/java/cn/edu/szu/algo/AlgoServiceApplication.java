package cn.edu.szu.algo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@SpringCloudApplication
@EnableFeignClients
@MapperScan("cn.edu.szu.algo.dao")
public class AlgoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlgoServiceApplication.class, args);
    }
}

