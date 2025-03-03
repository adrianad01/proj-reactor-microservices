package com.reactor.ms_customers.persistence;

import com.reactor.ms_customers.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public interface ICustomersRepository extends JpaRepository<Customer, Integer> {

    CompletableFuture<List<Customer>> findAllBy();

    CompletableFuture<Customer> findByIdCliente(Integer idCliente);

    CompletableFuture<Customer> findByNombre(String nombre);

    CompletableFuture<Optional<Customer>> findByRfc(String rfc);
}
