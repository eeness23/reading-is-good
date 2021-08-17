package com.enes.readingisgood.entity.converter;

import com.enes.readingisgood.enums.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrderStatus status) {
        return status.getValue();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer orderStatusCode) {
        return OrderStatus.of(orderStatusCode);
    }
}
