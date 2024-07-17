package br.com.rocketseat.hiokdev.planner_java.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidDateTimeValidator.class})
public @interface ValidDateTime {

    String message() default "formato de data inválido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
