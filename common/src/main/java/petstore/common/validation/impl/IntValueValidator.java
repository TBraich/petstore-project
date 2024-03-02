package petstore.common.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;
import petstore.common.validation.IntValue;

@Log4j2
public class IntValueValidator implements ConstraintValidator<IntValue, String> {

  @Override
  public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
    try {
      Integer.parseInt(number);
      return true;
    } catch (Exception ex) {
      log.error("Can't convert this string number, error validation {}", number);
      return false;
    }
  }
}
