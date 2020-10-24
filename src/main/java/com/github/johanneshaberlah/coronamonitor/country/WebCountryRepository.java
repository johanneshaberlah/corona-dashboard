 package com.github.johanneshaberlah.coronamonitor.country;

 import com.github.johanneshaberlah.coronamonitor.json.JsonReader;
 import com.google.common.collect.Lists;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonNull;
 import com.google.gson.JsonObject;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;

 import java.util.Collection;
 import java.util.Collections;
 import java.util.Optional;
 import java.util.stream.Collectors;

@Component
public final class WebCountryRepository implements CountryRepository {
  private static final String BASE_URL = "https://covid19.mathdro.id/api/countries";
  private static final String JSON_PATH = "countries";

  private JsonReader jsonReader;
  private CountryFactory countryFactory;

  @Autowired
  private WebCountryRepository(JsonReader jsonReader, CountryFactory countryFactory) {
    this.jsonReader = jsonReader;
    this.countryFactory = countryFactory;
  }

  @Override
  public Optional<Country> findByName(String name) {
    return collectCountries()
      .stream()
      .filter(country -> country.name().equalsIgnoreCase(name))
      .findFirst();
  }

  @Override
  public Optional<Country> findByShortcut(String shortcut) {
    return collectCountries()
      .stream()
      .filter(country -> country.shortcut().equalsIgnoreCase(shortcut))
      .findFirst();
  }

  @Override
  public Collection<Country> collectCountries() {
    JsonElement element = jsonReader.readJsonElement(BASE_URL);
    if (element.equals(JsonNull.INSTANCE)){
      System.err.println("Failed reading countries from url " + BASE_URL);
      return Collections.emptyList();
    }
    JsonObject json = element.getAsJsonObject();
    return Lists.newArrayList(json.get(JSON_PATH).getAsJsonArray().iterator())
      .stream()
      .map(JsonElement::getAsJsonObject)
      .map(countryFactory::of)
      .collect(Collectors.toList());
  }
}
