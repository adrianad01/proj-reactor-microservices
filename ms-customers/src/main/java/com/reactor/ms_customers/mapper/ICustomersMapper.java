package com.reactor.ms_customers.mapper;

import com.reactor.ms_customers.domain.dto.CustomerDTO;
import com.reactor.ms_customers.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICustomersMapper {

    CustomerDTO entityToDto(Customer customer);

    Customer dtoToEntity(CustomerDTO customerDTO);

}
