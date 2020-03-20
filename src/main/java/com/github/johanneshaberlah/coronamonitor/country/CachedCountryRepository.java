package com.github.johanneshaberlah.coronamonitor.country;

import com.google.common.base.Preconditions;
import com.google.common.base.Suppliers;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public final class CachedCountryRepository implements CountryRepository {
  private CountryRepository delegate;
  private Supplier<Collection<Country>> countrySupplier;

  private CachedCountryRepository(CountryRepository delegate) {
    this.delegate = delegate;
    countrySupplier = createCountrySupplier();
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
    return Suppliers.memoizeWithExpiration(() -> delegate.collectCountries(), 4, TimeUnit.HOURS);
  }

  public static CachedCountryRepository create(CountryRepository delegate){
    Preconditions.checkNotNull(delegate);
    return new CachedCountryRepository(delegate);
  }
}
