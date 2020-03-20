package com.github.johanneshaberlah.coronamonitor.country.province;

import com.github.johanneshaberlah.coronamonitor.json.UniformResourceLocatorFactory;
import com.github.johanneshaberlah.coronamonitor.country.Country;
import com.github.johanneshaberlah.coronamonitor.json.JsonReader;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public final class WebProvinceRepository implements ProvinceRepository {
  private static final String BASE_URL = "https://covid19.mathdro.id/api/countries/%s/confirmed";

  private JsonReader jsonReader;

  @Autowired
  private WebProvinceRepository(JsonReader jsonReader) {
    this.jsonReader = jsonReader;
  }

  @Override
  public Collection<Province> findProvincesOfCountry(Country parent) {
    JsonElement json = readJsonElement(parent);
    if (json == JsonNull.INSTANCE){
      return Collections.emptyList();
    }
    JsonArray array = json.getAsJsonArray();
    return Lists.newArrayList(array.iterator())
      .stream()
      .map(element -> Province.of(parent, element))
      .collect(Collectors.toList());
  }

  private JsonElement readJsonElement(Country parent){
    return jsonReader.readJsonObject(UniformResourceLocatorFactory.create(String.format(BASE_URL, parent.name())));
  }
}
