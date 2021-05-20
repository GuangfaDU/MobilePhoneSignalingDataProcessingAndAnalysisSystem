package cn.edu.szu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@SpringCloudApplication
@EnableFeignClients
public class GatewayApplication {

    @Bean(name="ipKeyResolver")
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String hostName =
                    Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
            return Mono.just(hostName);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
