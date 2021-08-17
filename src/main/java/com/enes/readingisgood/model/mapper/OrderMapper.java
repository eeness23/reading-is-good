package com.enes.readingisgood.model.mapper;

import com.enes.readingisgood.entity.OrderEntity;
import com.enes.readingisgood.model.response.CustomerOrderResponse;
import com.enes.readingisgood.model.response.OrderDetailsResponse;
import com.enes.readingisgood.model.response.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookMapper.class, UserMapper.class})
public interface OrderMapper {
    CustomerOrderResponse entityToCustomerOrderResponse(OrderEntity order);

    OrderResponse entityToOrderResponse(OrderEntity orderEntity);

    OrderDetailsResponse entityToOrderDetailsResponse(OrderEntity orderEntity);
}
