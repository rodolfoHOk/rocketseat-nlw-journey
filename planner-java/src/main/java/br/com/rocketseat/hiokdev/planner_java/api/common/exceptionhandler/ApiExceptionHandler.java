package br.com.rocketseat.hiokdev.planner_java.api.common.exceptionhandler;

import br.com.rocketseat.hiokdev.planner_java.api.common.dto.ProblemResponse;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.PlannerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String GENERIC_ERROR_MESSAGE = "Something went wrong, try again";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundExceptionHandler(NotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String description = ex.getMessage();
        ProblemResponse responseBody = ProblemResponse.builder()
                .status(status.value())
                .description(description)
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PlannerException.class)
    public ResponseEntity<?> plannerExceptionHandler(PlannerException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String description = ex.getMessage();
        ProblemResponse responseBody = ProblemResponse.builder()
                .status(status.value())
                .description(description)
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> uncaughtExceptionHandler(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String description = GENERIC_ERROR_MESSAGE;
        ProblemResponse responseBody = ProblemResponse.builder()
                .status(status.value())
                .description(description)
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), status, request);
    }

}
