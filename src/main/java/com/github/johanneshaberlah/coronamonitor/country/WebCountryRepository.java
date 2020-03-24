package com.github.johanneshaberlah.coronamonitor.country;

import com.github.johanneshaberlah.coronamonitor.json.JsonReader;
import com.github.johanneshaberlah.coronamonitor.json.UniformResourceLocatorFactory;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public final class WebCountryRepository implements CountryRepository {
  private static final String BASE_URL = "https://covid19.mathdro.id/api";
  private static final String PATH = "countries";
  private static final String PATH_FORMAT = "%s/%s";

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
    JsonObject json = createJsonObject();
    return collect(json.get(PATH).getAsJsonArray().iterator())
      .stream()
      .map(JsonElement::getAsJsonObject)
      .map(this::createCountry)
      .collect(Collectors.toList());
  }

  private <T> Collection<T> collect(Iterator<T> iterator) {
    return Lists.newArrayList(iterator);
  }

  private JsonObject createJsonObject(){
    return jsonReader.readJsonObject(
      UniformResourceLocatorFactory.create(String.format(PATH_FORMAT, BASE_URL, PATH))
    ).getAsJsonObject();
  }

  private Country createCountry(JsonObject object){
    return countryFactory.of(object);
  }
}
