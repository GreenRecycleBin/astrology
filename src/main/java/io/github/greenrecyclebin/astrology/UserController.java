package io.github.greenrecyclebin.astrology;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
  @GetMapping
  List<User> getUsers() {
    return List.of(
        new User.Builder("Daniel", "Le").birthday(LocalDate.of(1990, 9, 29)).build(),
        new User.Builder("Eugine", "Dang").birthday(LocalDate.of(1992, 12, 30)).build());
  }

  @PostMapping
  public ResponseEntity<Void> createUser(@RequestBody User user) {
    return ResponseEntity.created(URI.create("http://localhost:8080/users/" + UUID.randomUUID()))
        .build();
  }
}

@org.springframework.web.bind.annotation.ControllerAdvice(annotations = RestController.class)
class ControllerAdvice {
  @ExceptionHandler
  ResponseEntity<Set<ValidationError>> handle(
      // Thrown by Object AbstractJackson2HttpMessageConverter#readJavaType(JavaType javaType,
      // HttpInputMessage inputMessage) throws IOException.
      InvalidDefinitionException exception) throws Throwable {
    Throwable cause = exception.getCause();

    if (cause instanceof ConstraintViolationException) {
      return handle((ConstraintViolationException) cause);
    }

    throw exception;
  }

  @ExceptionHandler
  ResponseEntity<Set<ValidationError>> handle(ConstraintViolationException exception) {
    return ResponseEntity.badRequest()
        .body(ValidationError.from(exception.getConstraintViolations()));
  }
}
