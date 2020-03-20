package com.github.johanneshaberlah.coronamonitor.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class CountryNameConverter implements Converter<String, Optional<Country>> {
  private CountryCollector countryCollector;

  @Autowired
  private CountryNameConverter(CountryCollector countryCollector) {
    this.countryCollector = countryCollector;
  }

  @Override
  public Optional<Country> convert(String countryName) {
    return countryCollector
            .stream()
            .filter(country -> country.name().equalsIgnoreCase(countryName))
            .findFirst();
  }
}
