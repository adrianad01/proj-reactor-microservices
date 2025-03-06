package com.reactor.ms_countries.service;

import com.reactor.ms_countries.domain.dto.CountryDTO;
import com.reactor.ms_countries.mapper.ICountryMapper;
import com.reactor.ms_countries.persistence.ICountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements ICountryService {

    private final ICountryRepository countryRepository;
    private final ICountryMapper countryMapper;

    @Override
    public Flux<CountryDTO> findAllCountries(){
        return Mono.fromFuture(countryRepository::findAllBy)
                .flatMapMany(Flux::fromIterable)
                .map(countryMapper::entityToDto);
    }
}
