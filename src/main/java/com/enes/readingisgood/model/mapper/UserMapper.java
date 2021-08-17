package com.enes.readingisgood.model.mapper;

import com.enes.readingisgood.entity.UserEntity;
import com.enes.readingisgood.model.request.CustomerRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface UserMapper {
    UserEntity customerRequestToUserEntity(CustomerRequest customerRequest);

}
