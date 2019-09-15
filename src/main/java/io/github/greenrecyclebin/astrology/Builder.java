package io.github.greenrecyclebin.astrology;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Objects;

interface Builder<T> {
  Validator validator = ValidatorInitializer.validator;

  T buildInternal();

  default T build() throws ConstraintViolationException {
    var object = buildInternal();

    final var violations = validator.validate(object);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }

    return object;
  }
}

final class ValidatorInitializer {
  static Validator validator;

  static {
    validator = Validation.buildDefaultValidatorFactory().getValidator();

    Objects.requireNonNull(validator, "Validator must not be null.");
  }
}
