package com.github.johanneshaberlah.coronamonitor.country;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public final class CountryCollectorFactory {

  public CountryCollector create(Collection<Country> countries){
    return () -> countries;
  }
}
