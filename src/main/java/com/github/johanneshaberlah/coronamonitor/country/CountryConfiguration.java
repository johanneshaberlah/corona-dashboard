package com.github.johanneshaberlah.coronamonitor.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.util.Collection;

@Configuration
public class CountryConfiguration {

  @Bean
  @Primary
  @Autowired
  @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
  CountryRepository countryRepository(WebCountryRepository delegate) {
    return CachedCountryRepository.create(delegate);
  }

  @Bean
  @Autowired
  @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
  Collection<Country> countries(
          CountryRepository repository,
          CountryInfectionInformationRepository informationRepository
  ) {
    Collection<Country> countries = repository.collectCountries();
    informationRepository.applyCountryInfectionInformation(countries);
    return countries;
  }


  @Bean
  @Autowired
  CountryCollector countryCollector(
          Collection<Country> countries,
          CountryCollectorFactory factory) {
    return factory.create(countries);
  }
}
