package com.github.johanneshaberlah.coronamonitor.country;

import com.github.johanneshaberlah.coronamonitor.common.Optionals;
import com.github.johanneshaberlah.coronamonitor.global.InfectionInformation;
import com.github.johanneshaberlah.coronamonitor.global.InfectionInformationFactory;
import com.github.johanneshaberlah.coronamonitor.json.JsonReader;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
  public Optional<InfectionInformation> findByCountryAndDate(Country country, Date date) {
    Optional<JsonElement> element = findInformationByCountry(
      country, formatDailyUrl(date)
    );
    return element.map(jsonElement -> factory.of(jsonElement.getAsJsonObject()));
  }

  @Override
  public void applyCountryInfectionInformation(Collection<Country> countries) {
    countries.forEach(this::applyCountryInfectionInformation);
  }

  private void applyCountryInfectionInformation(Country country) {
    Optional<JsonElement> element = findInformationByCountry(country, BASE_URL);
    element.map(JsonElement::getAsJsonObject)
      .ifPresent(object -> {
        InfectionInformation information = factory.of(object);
        country.setInfectionInformation(information);
      });
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

  private String formatDailyUrl(Date date) {
    return DAILY_URL.replace("[date]", new SimpleDateFormat("MM-dd-yyyy").format(date));
  }
}
