package io.github.greenrecyclebin.astrology;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

class ValidationError {
  private String className;
  private String propertyPath;
  private String errorMessage;

  static Set<ValidationError> from(Set<ConstraintViolation<?>> violations) {
    Set<ValidationError> errors = new HashSet<>();

    for (Object o : violations) {
      ConstraintViolation v = (ConstraintViolation) o;

      ValidationError error = new ValidationError();
      error.className = v.getRootBeanClass().getCanonicalName();
      error.errorMessage = v.getMessage();
      error.propertyPath = v.getPropertyPath().toString();
      errors.add(error);
    }

    return errors;
  }

  public String getClassName() {
    return className;
  }

  public String getPropertyPath() {
    return propertyPath;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
