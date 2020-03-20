package com.github.johanneshaberlah.coronamonitor.country;

import java.util.Collection;
import java.util.stream.Stream;

public interface CountryCollector {

  Collection<Country> collect();

  default Stream<Country> stream(){
    return collect().stream();
  }

}
