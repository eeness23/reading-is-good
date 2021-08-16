package com.enes.readingisgood.service;

import java.util.List;
import java.util.Locale;

public interface I18nService {
    List<String> getLocalizationMessage(String key, Locale locale, String... args);
}
