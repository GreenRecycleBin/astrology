package io.github.greenrecyclebin.astrology;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableCaching
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }
}

@Configuration
class JacksonConfiguration {
  @Bean
  Jackson2ObjectMapperBuilderCustomizer registerParameterNamesModule() {
    return builder ->
        builder.modulesToInstall(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
  }
}
