package com.github.johanneshaberlah.coronamonitor.global;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class InfectionInformationFactory {
  private static final int DEFAULT_VALUE = 0;
  private static final String VALUE_SUB_PATH = "value";
  private static final String CONFIRMED_ATTRIBUTE = "confirmed";
  private static final String RECOVERED_ATTRIBUTE = "recovered";
  private static final String DEATHS_ATTRIBUTE = "deaths";

  public InfectionInformation of(JsonObject element) {
    if (!element.has(CONFIRMED_ATTRIBUTE)) {
      return empty();
    }
    return create(
      readValue(element, CONFIRMED_ATTRIBUTE),
      readValue(element, RECOVERED_ATTRIBUTE),
      readValue(element, DEATHS_ATTRIBUTE)
    );
  }

  public InfectionInformation ofDailyEntry(JsonObject entry){
    if (!entry.has(CONFIRMED_ATTRIBUTE)) {
      return empty();
    }
    return create(
      entry.get(CONFIRMED_ATTRIBUTE).getAsInt(),
      entry.get(RECOVERED_ATTRIBUTE).getAsInt(),
      entry.get(DEATHS_ATTRIBUTE).getAsInt()
    );
  }

  public static InfectionInformation create(int confirmed, int recovered, int deaths) {
    Preconditions.checkArgument(confirmed >= DEFAULT_VALUE);
    Preconditions.checkArgument(recovered >= DEFAULT_VALUE);
    Preconditions.checkArgument(deaths >= DEFAULT_VALUE);
    return new InfectionInformation(confirmed, recovered, deaths);
  }

  public InfectionInformation empty() {
    return create(DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE);
  }

  private int readValue(JsonObject element, String path) {
    return element.getAsJsonObject().get(path).getAsJsonObject().get(VALUE_SUB_PATH).getAsInt();
  }
}
