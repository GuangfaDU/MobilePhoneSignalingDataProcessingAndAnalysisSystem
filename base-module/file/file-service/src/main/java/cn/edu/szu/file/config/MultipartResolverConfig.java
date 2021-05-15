package cn.edu.szu.file.config;

import cn.edu.szu.file.resolver.CustomMultipartResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Configuration
public class MultipartResolverConfig {

    @Bean
    public MultipartResolver multipartResolver() {
        return new CustomMultipartResolver();
    }
}
