package com.enes.readingisgood.exception;

import com.enes.readingisgood.controller.BaseController;
import com.enes.readingisgood.model.response.ErrorResponse;
import com.enes.readingisgood.service.I18nService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleException(Exception exception, Locale locale) {

        log.error("An exception occurred.Details: ", exception);
        return generateErrorResponseFromKey("system.error.occurred", locale);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<?> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception,
                                                               Locale locale) {
        log.error("A http request method not supported exception occurred.Details: ", exception);
        return generateErrorResponseFromKey("common.client.methodNotSupported", locale, exception.getMethod());
    }

    @ExceptionHandler(JWTException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleJWTException(JWTException exception, Locale locale) {
        log.error("A JWT exception occurred. Details: ", exception);
        return generateErrorResponseFromKey(exception.getKey(), locale, exception.getArgs());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException exception, Locale locale) {
        log.error("An unauthorized exception occurred.Details: ", exception);
        return generateErrorResponseFromKey(exception.getKey(), locale, exception.getArgs());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception, Locale locale) {
        log.error("An user not found exception occurred.Details: ", exception);
        return generateErrorResponseFromKey(exception.getKey(), locale, exception.getArgs());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleInvalidArgumentException(
            MethodArgumentNotValidException exception, Locale locale) {
        log.error("An invalid argument exception occurred.Details: ", exception);
        return generateFieldErrorResponse(exception.getBindingResult(), locale);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception, Locale locale) {
        log.error("A method argument type mismatch exception occurred.Details: ", exception);
        return generateErrorResponseFromKey("common.client.typeMismatch", locale,
                exception.getName());
    }

    private ResponseEntity<?> generateFieldErrorResponse(BindingResult bindingResult,
                                                         Locale locale) {
        List<String> errorDetails = i18nService.getLocalizationMessage("common.client.requiredField", locale);
        String code = errorDetails.get(0);
        String message = errorDetails.get(1);

        HashMap<String, String> errorMap = new HashMap<>();
        bindingResult
                .getFieldErrors()
                .forEach(fieldError -> errorMap.put(fieldError.getField(), message));
        return respond(new ErrorResponse<>(code, errorMap));
    }

    private ResponseEntity<?> generateErrorResponseFromKey(String key, Locale locale, String... args) {
        List<String> errorDetails = i18nService.getLocalizationMessage(key, locale, args);
        return respond(new ErrorResponse<>(errorDetails.get(0), errorDetails.get(1)));
    }
}
