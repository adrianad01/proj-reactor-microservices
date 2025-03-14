package com.reactor.ms_customers.service;

import com.reactor.ms_customers.domain.dto.CustomerDTO;
import com.reactor.ms_customers.domain.responses.ResponseInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomersService {

    Mono<ResponseInfo> createCustomer(CustomerDTO customerDTO);

    Flux<CustomerDTO> getAllCustomers();

    Mono<CustomerDTO> getCustomerById(int idCustomer);

    Mono<ResponseInfo> updateCustomer(CustomerDTO customerDTO);
}
