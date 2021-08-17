package com.enes.readingisgood.exception;

import com.enes.readingisgood.controller.BaseController;
import com.enes.readingisgood.model.response.ErrorResponse;
import com.enes.readingisgood.service.I18nService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends BaseController {

    private final I18nService i18nService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception exception, Locale locale) {
        log.error("An exception occurred.Details: ", exception);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return generateErrorResponseFromKey("system.error.occurred", locale, httpStatus);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception,
                                                         Locale locale) {
        log.error("An access denied exception occurred.Details: ", exception);
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        return generateErrorResponseFromKey("auth.access-denied", locale, httpStatus);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception,
                                                               Locale locale) {
        log.error("A http request method not supported exception occurred.Details: ", exception);
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        return generateErrorResponseFromKey("client.method-not-supported", locale, httpStatus, exception.getMethod());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInvalidArgumentException(
            MethodArgumentNotValidException exception, Locale locale) {
        log.error("An invalid argument exception occurred.Details: ", exception);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return generateFieldErrorResponse(exception.getBindingResult(), locale, httpStatus);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException exception, Locale locale) {
        log.error("An unauthorized exception occurred.Details: ", exception);
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        return generateErrorResponseFromKey(exception.getKey(), locale, httpStatus, exception.getArgs());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, Locale locale) {
        log.error("A http message not readable exception occurred.Details: ", exception);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return generateErrorResponseFromKey("client.message-not-readable", locale, httpStatus);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception, Locale locale) {
        log.error("A method argument type mismatch exception occurred.Details: ", exception);
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        return generateErrorResponseFromKey("client.type-mismatch", locale, httpStatus, exception.getName());
    }

    @ExceptionHandler({NotFoundException.class, JWTException.class, UserAlreadyExistsException.class})
    public ResponseEntity<?> handleException(BaseException exception, Locale locale) {
        log.error("An not found exception occurred.Details: ", exception);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return generateErrorResponseFromKey(exception.getKey(), locale, httpStatus, exception.getArgs());
    }

    private ResponseEntity<?> generateFieldErrorResponse(BindingResult bindingResult,
                                                         Locale locale, HttpStatus status) {
        List<String> errorDetails = i18nService.getLocalizationMessage("dto.invalid.field", locale);
        String code = errorDetails.get(0);

        HashMap<String, String> errorMap = new HashMap<>();
        bindingResult
                .getFieldErrors()
                .forEach(fieldError -> {
                    String defaultMessage = fieldError.getDefaultMessage();
                    List<String> details = i18nService.getLocalizationMessage(defaultMessage, locale);
                    errorMap.put(fieldError.getField(), details.get(1));
                });
        return respond(new ErrorResponse<>(code, errorMap), status);
    }

    private ResponseEntity<?> generateErrorResponseFromKey(String key, Locale locale, HttpStatus status, String... args) {
        List<String> errorDetails = i18nService.getLocalizationMessage(key, locale, args);
        return respond(new ErrorResponse<>(errorDetails.get(0), errorDetails.get(1)), status);
    }
}
