package com.devcourse.kurlymurly.common.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI swagger() {
        return new OpenAPI().info(kurlyMurlyInfo());
    }

    private Info kurlyMurlyInfo() {
        return new Info().title("컬리멀리")
                .description("[규현팀] 마켓컬리 클론 프로젝트")
                .version("1.0.0");
    }
}
