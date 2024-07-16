package br.com.rocketseat.hiokdev.planner_java.domain.common.exception;

public class PlannerException extends RuntimeException {

    public PlannerException(String message) {
        super(message);
    }

    public PlannerException(String message, Throwable cause) {
        super(message, cause);
    }

}
