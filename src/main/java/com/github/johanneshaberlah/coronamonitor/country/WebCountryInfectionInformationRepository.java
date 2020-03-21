package com.github.johanneshaberlah.coronamonitor.country;

import com.github.johanneshaberlah.coronamonitor.global.InfectionInformation;
import com.github.johanneshaberlah.coronamonitor.json.JsonReader;
import com.github.johanneshaberlah.coronamonitor.json.UniformResourceLocatorFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public final class WebCountryInfectionInformationRepository implements CountryInfectionInformationRepository {
  private static final String BASE_URL = "https://covid19.mathdro.id/api/countries/%s";

  private JsonReader jsonReader;

  @Autowired
  private WebCountryInfectionInformationRepository(
    JsonReader jsonReader
  ) {
    this.jsonReader = jsonReader;
  }

  @Override
  public void applyCountryInfectionInformation(Collection<Country> countries) {
    countries
      .forEach(country -> {
        Optional<JsonElement> element = findInformationByNameOrIso(country);
        if (!element.isPresent()){
          return;
        }
        JsonObject object = element.get().getAsJsonObject();
        InfectionInformation information = InfectionInformation.of(object);
        country.appendInfectionInformation(information);
      });
  }

  private Optional<JsonElement> findInformationByNameOrIso(Country country){
    JsonElement result = jsonReader.readJsonObject(UniformResourceLocatorFactory.create(String.format(BASE_URL, country.name())));
    if (result == JsonNull.INSTANCE) {
      result = jsonReader.readJsonObject(UniformResourceLocatorFactory.create(String.format(BASE_URL, country.iso())));
      if (result == JsonNull.INSTANCE){
        return Optional.empty();
      }
    }
    return Optional.ofNullable(result);
  }
}
