package com.reactor.ms_countries.service;

import com.reactor.ms_countries.domain.dto.CountryDTO;
import reactor.core.publisher.Flux;

public interface ICountryService {

    Flux<CountryDTO> findAllCountries();
}
