package com.reactor.ms_customers.service;

import com.reactor.ms_customers.domain.dto.CustomerDTO;
import com.reactor.ms_customers.domain.responses.ResponseInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICustomersService {

    Mono<ResponseInfo> createCustomer(CustomerDTO customerDTO);
}
