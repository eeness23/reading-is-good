package com.enes.readingisgood.service;

import java.util.Locale;

public interface I18nService {
    String[] getLocalizationMessage(String key, Locale locale, String... args);

    String[] getLocalizationMessage(String key, String... args);
}
