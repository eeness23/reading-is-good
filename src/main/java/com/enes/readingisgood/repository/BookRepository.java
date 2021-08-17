package com.enes.readingisgood.repository;

import com.enes.readingisgood.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
    Optional<BookEntity> findById(Long id);
}
