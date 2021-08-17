package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.service.I18nService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class I18nServiceImpl implements I18nService {

    private final MessageSource messageSource;
    private static final Pattern MESSAGE_SEPARATOR_PATTERN = Pattern.compile(";");

    @Override
    @Transactional(readOnly = true)
    public List<String> getLocalizationMessage(String key, Locale locale, String... args) {
        String message;
        try {
            message = messageSource.getMessage(key, args, locale);
        } catch (NoSuchMessageException exception) {
            message = getDefaultMessage(locale);
        }
        return MESSAGE_SEPARATOR_PATTERN.splitAsStream(message).collect(Collectors.toList());
    }

    private String getDefaultMessage(Locale locale) {
        return messageSource.getMessage("default.missing.message", null, locale);
    }
}
