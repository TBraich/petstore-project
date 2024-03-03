package petstore.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import petstore.common.validation.impl.RequireValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequireValidator.class)
@Documented
public @interface Required {
  String message() default "This value is not allowed to be null";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
