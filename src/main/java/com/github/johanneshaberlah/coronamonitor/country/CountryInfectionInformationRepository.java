package com.github.johanneshaberlah.coronamonitor.country;

import com.github.johanneshaberlah.coronamonitor.global.InfectionInformation;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface CountryInfectionInformationRepository {

  Optional<InfectionInformation> findByCountryAndDate(Country country, Date date);
  void applyCountryInfectionInformation(Collection<Country> countries);

}
