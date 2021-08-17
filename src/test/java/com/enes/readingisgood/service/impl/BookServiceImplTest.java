package com.enes.readingisgood.service.impl;

import com.enes.readingisgood.entity.BookEntity;
import com.enes.readingisgood.exception.NotFoundException;
import com.enes.readingisgood.model.mapper.BookMapper;
import com.enes.readingisgood.model.request.BookRequest;
import com.enes.readingisgood.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @Test
    void findBookById_success() {
        BookEntity dummyBookEntity = getDummyBookEntity();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(dummyBookEntity));
        BookEntity result = bookService.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void findBookById_notFoundId_throwNotFoundException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bookService.findById(1L));
    }

    @Test
    void saveBookFromEntity_success() {
        BookEntity dummyBookEntity = getDummyBookEntity();
        when(bookRepository.save(dummyBookEntity)).thenReturn(dummyBookEntity);
        BookEntity result = bookService.saveBook(dummyBookEntity);
        assertEquals(dummyBookEntity.getId(), result.getId());
    }

    @Test
    void saveBookFromBookRequest_success() {
        BookRequest dummyBookRequest = getDummyBookRequest();
        BookEntity dummyBookEntity = getDummyBookEntity();
        when(bookMapper.bookRequestToEntity(dummyBookRequest)).thenReturn(dummyBookEntity);
        when(bookService.saveBook(dummyBookEntity)).thenReturn(dummyBookEntity);
        BookEntity result = bookService.saveBook(dummyBookRequest);
        assertEquals(dummyBookRequest.getName(), result.getName());
    }

    @Test
    void updateBookStock_success() {
        BookEntity dummyBookEntity = getDummyBookEntity();
        dummyBookEntity.setStock(10);
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.of(dummyBookEntity));
        bookService.updateBookStock(id, 20);
        assertEquals(20, dummyBookEntity.getStock());
    }


    public BookEntity getDummyBookEntity() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setName("test-book");
        bookEntity.setAuthor("test-author");
        bookEntity.setPrice(BigDecimal.TEN);
        bookEntity.setStock(10);
        return bookEntity;
    }

    public BookRequest getDummyBookRequest() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setName("test-book");
        bookRequest.setAuthor("test-author");
        bookRequest.setPrice(BigDecimal.TEN);
        bookRequest.setStock(10);
        return bookRequest;
    }
}