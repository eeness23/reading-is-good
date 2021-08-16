package com.enes.readingisgood.exception;

import com.enes.readingisgood.controller.BaseController;
import com.enes.readingisgood.model.ErrorResponse;
import com.enes.readingisgood.model.Response;
import com.enes.readingisgood.service.I18nService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Locale;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends BaseController {

    private final I18nService i18nService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<ErrorResponse<String>> handleException(Exception exception, Locale locale) {
        log.error("An error occurred! Details: ", exception);
        return generateErrorResponseFromKey("system.error.occurred", locale);
    }

    @ExceptionHandler(JWTException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse<String>> handleJWTException(JWTException exception, Locale locale) {
        log.error("An JWTException error occurred! Details: ", exception);
        return generateErrorResponseFromKey(exception.getKey(), locale, exception.getArgs());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response<ErrorResponse<String>> handleUnauthorizedException(UnauthorizedException exception, Locale locale) {
        log.error("An error occurred! Details: ", exception);
        return generateErrorResponseFromKey(exception.getKey(), locale, exception.getArgs());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse<String>> handleUserNotFoundException(UserNotFoundException exception, Locale locale) {
        log.error("An error occurred! Details: ", exception);
        return generateErrorResponseFromKey(exception.getKey(), locale, exception.getArgs());
    }


    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorResponse<HashMap<String, String>>> handleRequestPropertyBindingError(
            WebExchangeBindException webExchangeBindException, Locale locale) {
        log.debug("Bad request!", webExchangeBindException);
        return generateFieldErrorResponse(webExchangeBindException.getBindingResult(), locale);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Response<ErrorResponse<String>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, Locale locale) {
        log.trace("MethodArgumentTypeMismatchException occurred", methodArgumentTypeMismatchException);
        return generateErrorResponseFromKey("common.client.typeMismatch", locale,
                methodArgumentTypeMismatchException.getName());
    }

    private Response<ErrorResponse<HashMap<String, String>>> generateFieldErrorResponse(BindingResult bindingResult,
                                                                                        Locale locale) {
        String[] localizationMessage = i18nService.getLocalizationMessage("common.client.requiredField", locale);
        String code = localizationMessage[0];
        String message = localizationMessage[1];

        HashMap<String, String> errorMap = new HashMap<>();
        bindingResult
                .getFieldErrors()
                .forEach(fieldError -> errorMap.put(fieldError.getField(), message));
        return respond(new ErrorResponse<>(code, errorMap));
    }

    private Response<ErrorResponse<String>> generateErrorResponseFromKey(String key, Locale locale, String... args) {
        String[] localizationMessage = i18nService.getLocalizationMessage(key, locale, args);
        return respond(new ErrorResponse<>(localizationMessage[0], localizationMessage[1]));
    }
}
