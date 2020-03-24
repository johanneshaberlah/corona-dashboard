package com.github.johanneshaberlah.coronamonitor.global;

import com.github.johanneshaberlah.coronamonitor.json.JsonReader;
import com.github.johanneshaberlah.coronamonitor.json.UniformResourceLocatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class GlobalInformationProvider {
  private static final String BASE_URL = "https://covid19.mathdro.id/api/";

  private JsonReader jsonReader;
  private InfectionInformationFactory factory;

  @Autowired
  private GlobalInformationProvider(JsonReader jsonReader, InfectionInformationFactory factory) {
    this.jsonReader = jsonReader;
    this.factory = factory;
  }

  public InfectionInformation globalInfectionInformation() {
    return factory.of(
      jsonReader.readJsonObject(
        UniformResourceLocatorFactory.create(BASE_URL)
      ).getAsJsonObject()
    );
  }
}
