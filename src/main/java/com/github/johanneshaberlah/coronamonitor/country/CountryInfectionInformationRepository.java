package com.github.johanneshaberlah.coronamonitor.country;

import java.util.Collection;

public interface CountryInfectionInformationRepository {

  void applyCountryInfectionInformation(Collection<Country> countries);

}
