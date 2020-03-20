package com.github.johanneshaberlah.coronamonitor.global;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

public final class InfectionInformation {
  private int confirmed;
  private int recovered;
  private int deaths;

  private InfectionInformation(int confirmed, int recovered, int deaths) {
    this.confirmed = confirmed;
    this.recovered = recovered;
    this.deaths = deaths;
  }

  public int confirmed() {
    return confirmed;
  }

  public int recovered() {
    return recovered;
  }

  public int deaths() {
    return deaths;
  }

  public static InfectionInformation of(JsonObject element){
    return create(getValue(element, "confirmed"), getValue(element, "recovered"), getValue(element, "deaths"));
  }

  private static int getValue(JsonObject element, String path){
    return element.getAsJsonObject().get(path).getAsJsonObject().get("value").getAsInt();
  }

  public static InfectionInformation create(int confirmed, int recovered, int deaths){
    Preconditions.checkArgument(confirmed >= 0);
    Preconditions.checkArgument(recovered >= 0);
    Preconditions.checkArgument(deaths >= 0);
    return new InfectionInformation(confirmed, recovered, deaths);
  }
}
