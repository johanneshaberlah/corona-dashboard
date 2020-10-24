package com.github.johanneshaberlah.coronamonitor.country;

import com.github.johanneshaberlah.coronamonitor.common.Optionals;
import com.github.johanneshaberlah.coronamonitor.global.InfectionInformationFactory;
import com.github.johanneshaberlah.coronamonitor.json.JsonReader;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public final class WebCountryInfectionInformationRepository implements CountryInfectionInformationRepository {
  private static final String BASE_URL = "https://covid19.mathdro.id/api/countries/%s";
  private static final String DAILY_URL = BASE_URL + "/daily/[date]";

  private JsonReader jsonReader;
  private InfectionInformationFactory factory;

  @Autowired
  private WebCountryInfectionInformationRepository(JsonReader jsonReader, InfectionInformationFactory factory) {
    this.jsonReader = jsonReader;
    this.factory = factory;
  }

  @Override
  public void applyCountryInfectionInformation(Collection<Country> countries) {
    countries.forEach(this::applyCountryInfectionInformation);
  }

  private void applyCountryInfectionInformation(Country country) {
    System.out.println("Applying information for " + country.name());
    Optional<JsonElement> element = findInformationByCountry(country, BASE_URL);
    element
      .map(JsonElement::getAsJsonObject)
      .map(factory::of)
      .ifPresent(country::setInfectionInformation);
  }

  private Optional<JsonElement> findInformationByCountry(Country country, String url) {
    return Optionals.presentOptional(
      tryFindInformationBy(url, country.name()),
      tryFindInformationBy(url, country.iso()),
      tryFindInformationBy(url, country.shortcut())
    );
  }

  private Optional<JsonElement> tryFindInformationBy(String url, String value) {
    return Optionals.ofNullableJsonElement(
      jsonReader.readJsonElement(url, value)
    );
  }
}
