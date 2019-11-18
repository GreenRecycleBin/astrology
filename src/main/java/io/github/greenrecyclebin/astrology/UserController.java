package io.github.greenrecyclebin.astrology;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
  UserRepository repository;

  public UserController(UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  List<User> getUsers() {
    return List.of(
        new User.Builder("Daniel", "Le").birthday(LocalDate.of(1990, 9, 29)).build(),
        new User.Builder("Eugine", "Dang").birthday(LocalDate.of(1992, 12, 30)).build());
  }

  @PostMapping
  public ResponseEntity<String> createUser(@RequestBody User user) {
    try {
      user = repository.save(user);
    } catch (NonTransientDataAccessException e) {
      try (var stringWriter = new StringWriter();
          var printWriter = new PrintWriter(stringWriter)) {

        e.printStackTrace(printWriter);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(String.format("Failed to save the user: %s.", stringWriter.toString()));
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    return ResponseEntity.created(URI.create("http://localhost:8080/users/" + user.getUuid()))
        .build();
  }

  @GetMapping("/{firstName}/first")
  public User findFirstUserWithFirstName(@PathVariable("firstName") String firstName) {
    return repository.findFirstByFirstName(firstName);
  }

  @GetMapping("/{firstName}")
  public List<User> findUsersWithFirstName(@PathVariable("firstName") String firstName) {
    return repository.findByFirstName(firstName);
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
