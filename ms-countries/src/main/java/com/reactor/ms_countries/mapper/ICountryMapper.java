package com.reactor.ms_countries.mapper;

import com.reactor.ms_countries.domain.dto.CountryDTO;
import com.reactor.ms_countries.domain.entity.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICountryMapper {

    CountryDTO entityToDto(Country country);
    Country dtoToEntity(CountryDTO countryDTO);
}
