package com.github.johanneshaberlah.coronamonitor.daily;

import com.github.johanneshaberlah.coronamonitor.global.InfectionInformation;
import com.github.johanneshaberlah.coronamonitor.global.InfectionInformationFactory;
import com.github.johanneshaberlah.coronamonitor.json.JsonReader;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collection;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class SimpleDailyInformationProvider implements DailyInformationProvider {
  private static final String BASE_URL = "https://covid19.mathdro.id/api/daily/%s";
  private static final String DATE_FORMAT = "MM-dd-yyyy";
  private static final String COUNTRY_NAME_ATTRIBUTE = "countryRegion";

  private JsonReader jsonReader;
  private InfectionInformationFactory informationFactory;
  private SimpleDateFormat format;

  @Autowired
  private SimpleDailyInformationProvider(JsonReader jsonReader, InfectionInformationFactory informationFactory) {
    this.jsonReader = jsonReader;
    this.informationFactory = informationFactory;
    this.format = createDateFormat();
  }

  @Override
  public InfectionInformation provide(DailyInformationContext request) {
    InfectionInformation prototype = informationFactory.empty();

    readElements(request)
      .stream()
      .map(JsonElement::getAsJsonObject)
      .filter(element -> element.get(COUNTRY_NAME_ATTRIBUTE).getAsString().equalsIgnoreCase(request.country().name()))
      .map(informationFactory::ofDailyEntry)
      .forEach(prototype::add);
    return prototype;
  }

  private JsonArray readJsonArray(DailyInformationContext request){
    String route = String.format(BASE_URL, format.format(request.date()));
    return jsonReader.readJsonElement(route).getAsJsonArray();
  }

  private Collection<JsonElement> readElements(DailyInformationContext request){
    return Lists.newArrayList(readJsonArray(request).iterator());
  }

  private SimpleDateFormat createDateFormat(){
    return new SimpleDateFormat(DATE_FORMAT);
  }
}
