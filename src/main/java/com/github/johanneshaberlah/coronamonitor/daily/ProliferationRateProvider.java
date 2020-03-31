package com.github.johanneshaberlah.coronamonitor.daily;

import com.github.johanneshaberlah.coronamonitor.country.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class ProliferationRateProvider {
  private DailyInformationProvider dailyInformationProvider;

  @Autowired
  private ProliferationRateProvider(DailyInformationProvider dailyInformationProvider) {
    this.dailyInformationProvider = dailyInformationProvider;
  }

  public Proliferation calculateProliferationSince(Country country, Date date){
    DailyInformationContext request = DailyInformationContext.create(country, date);
    return Proliferation.calculate(dailyInformationProvider.provide(request), country.infectionInformation());
  }
}
