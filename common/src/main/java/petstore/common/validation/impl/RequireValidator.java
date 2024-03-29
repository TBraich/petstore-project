package petstore.common.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import petstore.common.validation.Required;

public class RequireValidator implements ConstraintValidator<Required, Object> {

  @Override
  public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
    return Objects.nonNull(o);
  }
}
