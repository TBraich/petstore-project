package petstore.common.validation;

import jakarta.validation.Constraint;
import petstore.common.validation.impl.RequireValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequireValidator.class)
@Documented
public @interface Required {}
