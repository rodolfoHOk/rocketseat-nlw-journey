package br.com.rocketseat.hiokdev.planner_java.api.common.exceptionhandler;

import br.com.rocketseat.hiokdev.planner_java.api.common.dto.FieldErrorResponse;
import br.com.rocketseat.hiokdev.planner_java.api.common.dto.ProblemResponse;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.NotFoundException;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.PlannerException;
import br.com.rocketseat.hiokdev.planner_java.domain.common.exception.ValidationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String GENERIC_ERROR_MESSAGE = "Something went wrong, try again";
    public static final String GENERIC_BAD_REQUEST_MESSAGE = "Invalid request content";

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String description = ex.getMessage();
        ProblemResponse responseBody = ProblemResponse.builder()
                .status(status.value())
                .description(description)
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<FieldErrorResponse> fieldsErrors = List.of(
                FieldErrorResponse.builder().name(ex.getField()).message(ex.getMessage()).build()
        );
        ProblemResponse responseBody = ProblemResponse.builder()
                .status(status.value())
                .description(GENERIC_BAD_REQUEST_MESSAGE)
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .fields(fieldsErrors)
                .build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PlannerException.class)
    public ResponseEntity<?> handlePlannerException(PlannerException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String description = ex.getMessage();
        ProblemResponse responseBody = ProblemResponse.builder()
                .status(status.value())
                .description(description)
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String description = ex.getBody().getDetail();
        List<FieldErrorResponse> fieldsErrors = ex.getBindingResult().getAllErrors().stream()
                .map(objectError -> {
                    String name = objectError.getObjectName();
                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    return FieldErrorResponse.builder()
                            .name(name)
                            .message(message)
                            .build();
                }).toList();
        ProblemResponse responseBody = ProblemResponse.builder()
                .status(status.value())
                .description(description)
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .fields(fieldsErrors)
                .build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String description = ex.getMessage();
        ProblemResponse responseBody = ProblemResponse.builder()
                .status(status.value())
                .description(description)
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String description = ex.getMessage();
        ProblemResponse responseBody = ProblemResponse.builder()
                .status(status.value())
                .description(description)
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, responseBody, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String description = ex.getMessage();
        ProblemResponse responseBody = ProblemResponse.builder()
                .status(status.value())
                .description(description)
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, responseBody, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            String description = String.format("Failed to convert '%s' with value: '%s'", ex.getPropertyName(), ex.getValue());
            ProblemResponse responseBody = ProblemResponse.builder()
                    .status(status.value())
                    .description(description)
                    .path(((ServletWebRequest)request).getRequest().getRequestURI())
                    .timestamp(LocalDateTime.now())
                    .build();
            return handleExceptionInternal(ex, responseBody, headers, status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String description = ex.getMessage();
        ProblemResponse responseBody = ProblemResponse.builder()
                .status(status.value())
                .description(description)
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return handleExceptionInternal(ex, responseBody, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (body == null) {
            body = ProblemResponse.builder()
                    .status(statusCode.value())
                    .description(GENERIC_ERROR_MESSAGE)
                    .path(((ServletWebRequest)request).getRequest().getRequestURI())
                    .timestamp(LocalDateTime.now())
                    .build();
        } else if (body instanceof String) {
            body = ProblemResponse.builder()
                    .status(statusCode.value())
                    .description((String) body)
                    .path(((ServletWebRequest)request).getRequest().getRequestURI())
                    .timestamp(LocalDateTime.now())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

}
