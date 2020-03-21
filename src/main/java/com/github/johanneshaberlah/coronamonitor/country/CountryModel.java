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
  private CountryRepository repository;
  private GlobalInformationProvider globalInformationProvider;

  @Autowired
  private CountryModel(CountryRepository repository, GlobalInformationProvider globalInformationProvider) {
    this.repository = repository;
    this.globalInformationProvider = globalInformationProvider;
  }

  Collection<Country> findMostInfectedCountries(int limit) {
    return this.repository.collectCountries().stream()
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
