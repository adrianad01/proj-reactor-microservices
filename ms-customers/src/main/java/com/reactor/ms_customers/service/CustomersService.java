package com.reactor.ms_customers.service;

import com.reactor.ms_customers.domain.dto.CustomerDTO;
import com.reactor.ms_customers.domain.entity.Customer;
import com.reactor.ms_customers.domain.responses.ResponseInfo;
import com.reactor.ms_customers.mapper.ICustomersMapper;
import com.reactor.ms_customers.persistence.ICustomersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

import static com.reactor.ms_customers.domain.constants.MessageConstants.INFORMACION_GUARDADA;
import static com.reactor.ms_customers.domain.constants.MessageConstants.RFC_EXISTENTE;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomersService implements ICustomersService {
    private final ICustomersMapper customersMapper;
    private final ICustomersRepository customersRepository;

    @Transactional
    @Override
    public Mono<ResponseInfo> createCustomer(CustomerDTO customerDTO) {
        ResponseInfo responseInfo = new ResponseInfo();
        Customer customerEntity = customersMapper.dtoToEntity(customerDTO);

        if (customerEntity.getFechaAlta() == null) {
            customerEntity.setFechaAlta(LocalDateTime.now());
        }

        return Mono.fromFuture(() -> customersRepository.findByRfc(customerEntity.getRfc()))
                .flatMap(optionalRfc -> optionalRfc
                        .map(existingCustomer -> {
                            ResponseInfo.fillResponse(responseInfo, false, 1, RFC_EXISTENTE);
                            return Mono.just(responseInfo);
                        })
                        .orElseGet(() -> saveCustomer(customerEntity)
                                .then(Mono.fromCallable(() -> {
                                    ResponseInfo.fillResponse(responseInfo, true, 0, INFORMACION_GUARDADA);
                                    return responseInfo;
                                }))
                        )
                );
    }

    private Mono <Customer> saveCustomer(Customer customerEntity) {
        return Mono.fromCallable(() -> customersRepository.save(customerEntity))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<CustomerDTO> getAllCustomers() {
        return Mono.fromFuture(customersRepository::findAllBy)
                .flatMapMany(customers -> Flux.fromIterable(customers)
                        .map(customersMapper::entityToDto));
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(int idCustomer){
        return Mono.fromFuture(()-> customersRepository.findByIdCliente(idCustomer))
                .map(customersMapper::entityToDto);
    }

    @Transactional
    @Override
    public Mono<ResponseInfo> updateCustomer(CustomerDTO customerDTO) {
        ResponseInfo responseInfo = new ResponseInfo();
        return Mono.fromFuture(() -> customersRepository.findByIdCliente(customerDTO.getIdCliente()))
                .flatMap(existingCustomer -> {
                    if (existingCustomer == null) {
                        ResponseInfo.fillResponse(responseInfo, false, 0, "El cliente no existe, se debe crear");
                        return Mono.just(responseInfo);
                    }
                    Customer updatedCustomer = customersMapper.dtoToEntity(customerDTO);
                    updatedCustomer.setIdCliente(existingCustomer.getIdCliente());
                    return Mono.fromCallable(() -> customersRepository.save(updatedCustomer))
                            .subscribeOn(Schedulers.boundedElastic())
                            .map(savedCustomer -> {
                                ResponseInfo.fillResponse(responseInfo, true, 0, "Cliente actualizado correctamente");
                                return responseInfo;
                            });
                });
    }
}
