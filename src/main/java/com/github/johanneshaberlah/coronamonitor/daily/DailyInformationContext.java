package com.github.johanneshaberlah.coronamonitor.daily;

import com.github.johanneshaberlah.coronamonitor.country.Country;
import com.google.common.base.Preconditions;

import java.util.Date;

public final class DailyInformationContext {
  private Country country;
  private Date date;

  private DailyInformationContext(Country country, Date date) {
    this.country = country;
    this.date = date;
  }

  public Country country() {
    return country;
  }

  public Date date() {
    return date;
  }

  public static DailyInformationContext create(Country country, Date date){
    Preconditions.checkNotNull(country);
    Preconditions.checkNotNull(date);
    return new DailyInformationContext(country, date);
  }
}
