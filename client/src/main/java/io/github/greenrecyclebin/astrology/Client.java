package io.github.greenrecyclebin.astrology;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

public class Client {
  public static void main(String[] args) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
    objectMapper.registerModule(new JavaTimeModule());

    ExchangeStrategies jacksonStrategy =
        ExchangeStrategies.builder()
            .codecs(
                config -> {
                  config
                      .defaultCodecs()
                      .jackson2JsonDecoder(
                          new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                })
            .build();

    Mono<User[]> result =
        WebClient.builder()
            .baseUrl("http://localhost:8080/users")
            .exchangeStrategies(jacksonStrategy)
            .build()
            .get()
            .retrieve()
            .bodyToMono(User[].class);

    result.subscribe(
        users -> {
          for (var user : users) {
            System.out.println(user);
          }
        });

    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
