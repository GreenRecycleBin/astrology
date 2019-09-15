package io.github.greenrecyclebin.astrology;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonDeserialize(builder = User.Builder.class)
class User {
  private String firstName;

  @NotNull private String lastName;

  private LocalDate birthday;

  private User() {}

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  @Override
  public String toString() {
    return "User{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", birthday=" + birthday +
            '}';
  }

  @JsonPOJOBuilder(withPrefix = "")
  static class Builder implements io.github.greenrecyclebin.astrology.Builder<User> {
    private final String firstName;
    private final String lastName;

    private LocalDate birthday;

    @JsonCreator
    Builder(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
    }

    Builder birthday(LocalDate birthday) {
      this.birthday = birthday;

      return this;
    }

    public User buildInternal() {
      User user = new User();
      user.firstName = firstName;
      user.lastName = lastName;
      user.birthday = birthday;

      return user;
    }
  }
}
