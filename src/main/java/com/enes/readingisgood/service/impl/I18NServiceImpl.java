package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.configuration.I18nConfiguration;
import com.enes.readingisgood.service.I18nService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class I18NServiceImpl implements I18nService {

    private final MessageSource messageSource;
    private final I18nConfiguration i18nConfiguration;
    private static final String EXCEPTION_MESSAGE_SEPARATOR = ";";

    @Override
    public String[] getLocalizationMessage(String key, Locale locale, String... args) {
        String message = messageSource.getMessage(key, args, locale);
        return message.split(EXCEPTION_MESSAGE_SEPARATOR);
    }

    @Override
    public String[] getLocalizationMessage(String key, String... args) {
        Locale locale = new Locale(i18nConfiguration.getDefaultLocale());
        return getLocalizationMessage(key, locale, args);
    }
}
