package com.enes.readingisgood.model.mapper;

import com.enes.readingisgood.entity.OrderEntity;
import com.enes.readingisgood.model.response.CustomerOrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface OrderMapper {
    CustomerOrderResponse entityToCustomerOrderResponse(OrderEntity order);
}
