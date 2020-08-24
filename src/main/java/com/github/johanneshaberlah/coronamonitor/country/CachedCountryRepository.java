package com.github.johanneshaberlah.coronamonitor.country;

import com.github.johanneshaberlah.coronamonitor.common.MoreSuppliers;
import com.github.johanneshaberlah.coronamonitor.common.TimeAndUnit;
import com.github.johanneshaberlah.coronamonitor.daily.ProliferationRateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
@Primary
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class CachedCountryRepository implements CountryRepository {
  private CountryRepository delegate;
  private CountryInfectionInformationRepository informationRepository;
  private ProliferationRateProvider proliferationProvider;
  private Supplier<Collection<Country>> countrySupplier;

  @Autowired
  private CachedCountryRepository(
    CountryRepository delegate,
    CountryInfectionInformationRepository informationRepository,
    ProliferationRateProvider proliferationProvider
  ) {
    this.delegate = delegate;
    this.informationRepository = informationRepository;
    this.proliferationProvider = proliferationProvider;
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

  private Supplier<Collection<Country>> createCountrySupplier() {
    return MoreSuppliers.concurrentRefreshingSupplier(() -> {
      Collection<Country> countries = delegate.collectCountries();
      informationRepository.applyCountryInfectionInformation(countries);
      countries.forEach(this::applyProliferationRate);
      return countries;
    }, TimeAndUnit.create(TimeUnit.MINUTES, 30));
  }

  private void applyProliferationRate(Country country) {
    country.setProliferationSinceYesterday(
      proliferationProvider.calculateProliferationSince(country, dayBeforeYesterday())
    );
  }

  private Date dayBeforeYesterday() {
    return Date.from(Instant.now().minus(2, ChronoUnit.DAYS));
  }
}
