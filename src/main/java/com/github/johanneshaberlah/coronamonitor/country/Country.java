package com.github.johanneshaberlah.coronamonitor.country;

import com.github.johanneshaberlah.coronamonitor.country.province.Province;
import com.github.johanneshaberlah.coronamonitor.global.InfectionInformation;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;

public final class Country {
  private String name;
  private String iso;
  private String shortcut;
  private Collection<Province> provinces;

  private InfectionInformation infectionInformation;

  private Country(
    String name,
    String iso,
    String shortcut,
    Collection<Province> provinces
  ) {
    this.name = name;
    this.iso = iso;
    this.shortcut = shortcut;
    this.provinces = provinces;
  }

  public String name() {
    return name;
  }

  public String iso() { return iso; }

  public String shortcut() {
    return shortcut;
  }

  public Collection<Province> provinces() {
    return Collections.unmodifiableCollection(provinces);
  }

  public Country appendProvinces(Collection<Province> provinces) {
    Preconditions.checkNotNull(provinces);
    this.provinces = provinces;
    return this;
  }

  public InfectionInformation infectionInformation() {
    return infectionInformation;
  }

  @Override
  public int hashCode() {
    return iso.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this){
      return true;
    }

    if (!(other instanceof Country)){
      return false;
    }
    Country that = (Country) other;
    return that.iso().equals(iso);
  }

  public Country appendInfectionInformation(InfectionInformation information){
    Preconditions.checkNotNull(information);
    this.infectionInformation = information;
    return this;
  }

  public static Country createWithoutProvinces(String name, String iso, String shortcut) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(iso);
    Preconditions.checkNotNull(shortcut);
    return create(name, iso, shortcut, Lists.newArrayList());
  }

  public static Country create(String name, String iso, String shortcut, Collection<Province> provinces) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(iso);
    Preconditions.checkNotNull(shortcut);
    Preconditions.checkNotNull(provinces);
    return new Country(name, iso, shortcut, provinces);
  }
}
