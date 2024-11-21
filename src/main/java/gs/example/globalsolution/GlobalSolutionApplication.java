package gs.example.globalsolution;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GlobalSolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobalSolutionApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API globalSolution")
                        .version("1.0.0")
                        .description("API responsável por gerir dados do dispositivo da GS."));
    }
}
