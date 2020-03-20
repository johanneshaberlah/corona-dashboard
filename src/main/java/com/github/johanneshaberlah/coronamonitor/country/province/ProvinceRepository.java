package com.github.johanneshaberlah.coronamonitor.country.province;

import com.github.johanneshaberlah.coronamonitor.country.Country;

import java.util.Collection;

public interface ProvinceRepository {

  Collection<Province> findProvincesOfCountry(Country country);

}
