package com.github.johanneshaberlah.coronamonitor.country;

import com.github.johanneshaberlah.coronamonitor.global.GlobalInformationProvider;
import com.github.johanneshaberlah.coronamonitor.global.InfectionInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public final class CountryModel {
  private CountryCollector countryCollector;
  private GlobalInformationProvider globalInformationProvider;

  @Autowired
  private CountryModel(CountryCollector countryCollector, GlobalInformationProvider globalInformationProvider) {
    this.countryCollector = countryCollector;
    this.globalInformationProvider = globalInformationProvider;
  }

  Collection<Country> findMostInfectedCountries(int limit) {
    return this.countryCollector.stream()
            .distinct()
            .filter(country -> country.infectionInformation() != null)
            .sorted((left, right) -> Integer.compare(right.infectionInformation().confirmed(), left.infectionInformation().confirmed()))
            .limit(limit)
            .collect(Collectors.toList());
  }

  InfectionInformation globalInfectionInformation(){
    return globalInformationProvider.globalInfectionInformation();
  }
}
