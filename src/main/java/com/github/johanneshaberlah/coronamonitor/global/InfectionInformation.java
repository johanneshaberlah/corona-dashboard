package com.github.johanneshaberlah.coronamonitor.global;

public final class InfectionInformation {
  private int confirmed;
  private int recovered;
  private int deaths;

  InfectionInformation(int confirmed, int recovered, int deaths) {
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

}
