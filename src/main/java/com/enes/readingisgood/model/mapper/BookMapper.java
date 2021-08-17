package com.enes.readingisgood.model.mapper;

import com.enes.readingisgood.entity.BookEntity;
import com.enes.readingisgood.model.response.BookResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookResponse entityToBookResponse(BookEntity book);
}
