package com.enes.readingisgood.controller;

import com.enes.readingisgood.model.request.BookRequest;
import com.enes.readingisgood.model.request.BookStockRequest;
import com.enes.readingisgood.model.response.Response;
import com.enes.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController extends BaseController {

    private final BookService bookService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Response<Long>> saveBook(@RequestBody @Valid BookRequest bookRequest) {
        Long id = bookService.saveBook(bookRequest).getId();
        return respond(id, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{bookId}/stock")
    public ResponseEntity<Void> updateBookStock(@PathVariable Long bookId,
                                                @RequestBody @Valid BookStockRequest bookStockRequest) {
        bookService.updateBookStock(bookId, bookStockRequest.getStock());
        return respondSuccess();
    }
}
