package com.github.johanneshaberlah.coronamonitor.country;

import java.util.Collection;
import java.util.Optional;

public interface CountryRepository {

  Optional<Country> findByName(String name);
  Optional<Country> findByShortcut(String shortcut);
  Collection<Country> collectCountries();

}
