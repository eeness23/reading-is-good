package com.enes.readingisgood.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class I18nServiceImplTest {

    @InjectMocks
    private I18nServiceImpl i18nService;
    @Mock
    private MessageSource messageSource;

    @Test
    void getLocalizationMessage_turkish_success() {
        Locale locale = new Locale("tr");
        String key = "test";
        String[] args = new String[0];

        when(messageSource.getMessage(key, args, locale)).thenReturn("111;selam");
        List<String> result = i18nService.getLocalizationMessage(key, locale);

        assertEquals("111", result.get(0));
        assertEquals("selam", result.get(1));
    }

    @Test
    void getLocalizationMessage_english_success() {
        Locale locale = new Locale("en");
        String key = "test";
        String[] args = new String[0];

        when(messageSource.getMessage(key, args, locale)).thenReturn("111;hi");
        List<String> result = i18nService.getLocalizationMessage(key, locale);

        assertEquals("111", result.get(0));
        assertEquals("hi", result.get(1));
    }

    @Test
    void localizationMessageFailed_returnDefaultVariable() {
        Locale locale = new Locale("en");
        String key = "test";
        String[] args = new String[0];

        when(messageSource.getMessage(key, args, locale)).thenThrow(NoSuchMessageException.class);

        when(messageSource.getMessage("default.missing.message", null, locale)).thenReturn("000;default");
        List<String> result = i18nService.getLocalizationMessage(key, locale);

        assertEquals("000", result.get(0));
        assertEquals("default", result.get(1));
    }
}