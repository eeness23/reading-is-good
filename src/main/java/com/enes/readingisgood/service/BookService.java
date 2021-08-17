package com.enes.readingisgood.service;

import com.enes.readingisgood.entity.BookEntity;

public interface BookService {
    BookEntity findById(Long id);
}
