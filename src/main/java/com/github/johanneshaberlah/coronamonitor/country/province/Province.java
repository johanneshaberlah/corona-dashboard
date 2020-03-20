package com.github.johanneshaberlah.coronamonitor.country.province;

import com.github.johanneshaberlah.coronamonitor.country.Country;
import com.github.johanneshaberlah.coronamonitor.global.InfectionInformation;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

public final class Province {
  private Country parent;
  private String name;

  private InfectionInformation infectionInformation;

  private Province(Country parent, String name) {
    this.parent = parent;
    this.name = name;
  }

  public Country parent() {
    return parent;
  }

  public String name() {
    return name;
  }

  public InfectionInformation infectionInformation() {
    return infectionInformation;
  }

  public Province appendInfectionInformation(InfectionInformation infectionInformation){
    Preconditions.checkNotNull(infectionInformation);
    this.infectionInformation = infectionInformation;
    return this;
  }

  public static Province of(Country parent, JsonElement element){
    JsonElement jsonElement = element.getAsJsonObject().get("provinceState");
    if (jsonElement == JsonNull.INSTANCE){
      return Province.create(parent, element.getAsJsonObject().get("countryRegion").getAsString());
    }
    return Province.create(parent, jsonElement.getAsString());
  }

  public static Province create(Country parent, String name){
    Preconditions.checkNotNull(parent);
    Preconditions.checkNotNull(name);
    return new Province(parent, name);
  }
}
