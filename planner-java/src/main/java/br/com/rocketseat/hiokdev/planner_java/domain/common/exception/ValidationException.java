package br.com.rocketseat.hiokdev.planner_java.domain.common.exception;

import lombok.Getter;

@Getter
public class ValidationException extends PlannerException {

    private final String field;

    public ValidationException(String field, String message) {
        super(message);
        this.field = field;
    }

}
