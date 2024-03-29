package hu.ponte.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI custom() {
        return new OpenAPI()
                .info(new Info()
                        .title("PonteApplication")
                        .version("1.0.1")
                        .description("The backend part of the task given by Ponte.hu."));
    }
}
