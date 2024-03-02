package petstore.common.validation;

import jakarta.validation.Constraint;
import petstore.common.validation.impl.RequireValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequireValidator.class)
@Documented
public @interface NotBlank {}
