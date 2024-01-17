package petstore.user.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import petstore.user.validation.Required;

public class RequireValidator implements ConstraintValidator<Required, Object> {

  @Override
  public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
//    constraintValidatorContext.disableDefaultConstraintViolation();
//    constraintValidatorContext
//        .buildConstraintViolationWithTemplate("Param is missing!")
//        .addConstraintViolation();
    System.out.println("been here");
    return Objects.nonNull(o);
  }
}
