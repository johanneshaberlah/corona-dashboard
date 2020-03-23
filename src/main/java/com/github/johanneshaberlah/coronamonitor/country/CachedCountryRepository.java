package com.github.johanneshaberlah.coronamonitor.country;

import com.github.johanneshaberlah.coronamonitor.common.RefreshingSupplier;
import com.github.johanneshaberlah.coronamonitor.common.TimeAndUnit;
import com.google.common.base.Suppliers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
@Primary
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class CachedCountryRepository implements CountryRepository {
  private CountryRepository delegate;
  private CountryInfectionInformationRepository informationRepository;
  private Supplier<Collection<Country>> countrySupplier;

  @Autowired
  private CachedCountryRepository(
    CountryRepository delegate,
    CountryInfectionInformationRepository informationRepository
  ) {
    this.delegate = delegate;
    this.informationRepository = informationRepository;
    countrySupplier = createCountrySupplier();
    collectCountries();
  }

  @Override
  public Optional<Country> findByName(String name) {
    return delegate.findByName(name);
  }

  @Override
  public Optional<Country> findByShortcut(String shortcut) {
    return delegate.findByShortcut(shortcut);
  }

  @Override
  public Collection<Country> collectCountries() {
    return countrySupplier.get();
  }

  private Supplier<Collection<Country>> createCountrySupplier(){
    return RefreshingSupplier.create(() -> {
      Collection<Country> countries = delegate.collectCountries();
      informationRepository.applyCountryInfectionInformation(countries);
      return countries;
    }, TimeAndUnit.create(TimeUnit.HOURS, 2));
  }
}
