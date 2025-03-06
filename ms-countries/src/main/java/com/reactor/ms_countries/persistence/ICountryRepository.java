package com.reactor.ms_countries.persistence;

import com.reactor.ms_countries.domain.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Integer> {

    CompletableFuture<List<Country>> findAllBy();
}
