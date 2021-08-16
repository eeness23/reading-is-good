package com.enes.readingisgood.entity.converter;

import com.enes.readingisgood.enums.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Status status) {
        return status.getValue();
    }

    @Override
    public Status convertToEntityAttribute(Integer statusCode) {
        return Status.of(statusCode);
    }
}
