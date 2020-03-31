package com.github.johanneshaberlah.coronamonitor.country;

import com.github.johanneshaberlah.coronamonitor.daily.Proliferation;
import com.github.johanneshaberlah.coronamonitor.global.InfectionInformation;
import com.google.common.base.MoreObjects;

public final class Country {
  private String name;
  private String iso;
  private String shortcut;

  private InfectionInformation infectionInformation;
  private Proliferation proliferationSinceYesterday;

  Country(
    String name,
    String iso,
    String shortcut
  ) {
    this.name = name;
    this.iso = iso;
    this.shortcut = shortcut;
  }

  public String name() {
    return name;
  }

  public String iso() {
    return iso;
  }

  public String shortcut() {
    return shortcut;
  }

  public InfectionInformation infectionInformation() {
    return infectionInformation;
  }

  public Proliferation proliferationSinceYesterday() {
    return proliferationSinceYesterday;
  }

  public void setInfectionInformation(InfectionInformation infectionInformation){
    this.infectionInformation = infectionInformation;
  }

  public void setProliferationSinceYesterday(Proliferation proliferationSinceYesterday) {
    this.proliferationSinceYesterday = proliferationSinceYesterday;
  }

  @Override
  public int hashCode() {
    return iso.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Country)) {
      return false;
    }
    Country that = (Country) other;
    return that.iso().equals(iso);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("name", name)
      .add("iso", iso)
      .add("shortcut", shortcut)
      .add("infectionInformation", infectionInformation)
      .toString();
  }
}
