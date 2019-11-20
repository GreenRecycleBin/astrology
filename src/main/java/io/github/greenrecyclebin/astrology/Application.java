package io.github.greenrecyclebin.astrology;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableCaching
public class Application {
  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    LOGGER.info("Starting app.");

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
