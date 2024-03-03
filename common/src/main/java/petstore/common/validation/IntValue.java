package petstore.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import petstore.common.validation.impl.IntValueValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IntValueValidator.class)
@Documented
public @interface IntValue {
  String message() default "This value is not an integer";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
