package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.BookEntity;
import com.enes.readingisgood.exception.NotFoundException;
import com.enes.readingisgood.model.mapper.BookMapper;
import com.enes.readingisgood.model.request.BookRequest;
import com.enes.readingisgood.repository.BookRepository;
import com.enes.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public BookEntity findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("book.not-found"));
    }

    @Override
    public BookEntity saveBook(BookRequest bookRequest) {
        BookEntity bookEntity =
                bookMapper.bookRequestToEntity(bookRequest);
        return saveBook(bookEntity);
    }

    @Override
    public BookEntity saveBook(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    @Override
    public void updateBookStock(Long bookId, int stock) {
        BookEntity bookEntity = findById(bookId);
        bookEntity.setStock(stock);
        saveBook(bookEntity);
    }
}
