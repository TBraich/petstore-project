package petstore.common.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import petstore.common.validation.NotBlank;

public class NotBlankValidator implements ConstraintValidator<NotBlank, Object> {

  @Override
  public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
    if (o instanceof String) {
      return StringUtils.isNotBlank((CharSequence) o);
    } else {
      return ObjectUtils.isNotEmpty(o);
    }
  }
}
