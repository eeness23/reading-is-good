package com.enes.readingisgood.service;

import com.enes.readingisgood.entity.BookEntity;
import com.enes.readingisgood.model.request.BookRequest;

public interface BookService {
    BookEntity findById(Long id);

    BookEntity saveBook(BookRequest bookRequest);

    BookEntity saveBook(BookEntity bookEntity);

    void updateBookStock(Long bookId, int stock);
}
