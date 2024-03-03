package petstore.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import petstore.common.validation.impl.NotBlankValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotBlankValidator.class)
@Documented
public @interface NotBlank {
  String message() default "This string value is not allowed to be blank";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
