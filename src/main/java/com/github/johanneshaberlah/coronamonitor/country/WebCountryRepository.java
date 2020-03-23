package com.github.johanneshaberlah.coronamonitor.country;

import com.github.johanneshaberlah.coronamonitor.json.UniformResourceLocatorFactory;
import com.github.johanneshaberlah.coronamonitor.country.province.ProvinceRepository;
import com.github.johanneshaberlah.coronamonitor.json.JsonReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public final class WebCountryRepository implements CountryRepository {
  private static final String BASE_URL = "https://covid19.mathdro.id/api";
  private static final String PATH = "countries";
  private static final String PATH_FORMAT = "%s/%s";
  private static final String ISO_STYLE = "iso3";

  private JsonReader jsonReader;

  @Autowired
  private WebCountryRepository(JsonReader jsonReader) {
    this.jsonReader = jsonReader;
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
    return json.get(PATH).getAsJsonObject().entrySet()
      .stream()
      .map(this::createCountry)
      .collect(Collectors.toList());
  }

  private String findIsoByShortcut(String shortcut){
    JsonObject json = createJsonObject();
    return json.get(ISO_STYLE).getAsJsonObject().entrySet()
      .stream()
      .filter(entry -> entry.getKey().equalsIgnoreCase(shortcut))
      .map(entry -> entry.getValue().getAsString())
      .findFirst()
      .orElse(null);
  }

  private JsonObject createJsonObject(){
    return jsonReader.readJsonObject(
      UniformResourceLocatorFactory.create(String.format(PATH_FORMAT, BASE_URL, PATH))
    ).getAsJsonObject();
  }

  private Country createCountry(Map.Entry<String, JsonElement> entry){
    Country prototype = Country.createWithoutProvinces(entry.getKey(), findIsoByShortcut(entry.getValue().getAsString()), entry.getValue().getAsString());
    // return prototype.appendProvinces(provinceRepository.findProvincesOfCountry(prototype));
    return prototype;
  }
}
