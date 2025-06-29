package Server.Pororing.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI pororingAPI() {
        Info info = new Info()
                .title("Pororing API")
                .description("포로링 API")
                .version("1.0.0");

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info);
    }
}