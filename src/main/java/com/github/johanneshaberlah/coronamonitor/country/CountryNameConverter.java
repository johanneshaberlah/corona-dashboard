package com.github.johanneshaberlah.coronamonitor.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public final class CountryNameConverter implements Converter<String, Optional<Country>> {
  private CountryRepository repository;

  @Autowired
  private CountryNameConverter(CountryRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Country> convert(String countryName) {
    return repository
            .collectCountries()
            .stream()
            .filter(country -> country.name().equalsIgnoreCase(countryName))
            .findFirst();
  }
}
