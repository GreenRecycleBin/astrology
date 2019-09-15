package io.github.greenrecyclebin.astrology;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class Client {
  public static void main(String[] args) {
    Flux<User> users = WebClient.builder().baseUrl("http://localhost:8080/users").build().get().retrieve().bodyToFlux(User.class);
  }
}
