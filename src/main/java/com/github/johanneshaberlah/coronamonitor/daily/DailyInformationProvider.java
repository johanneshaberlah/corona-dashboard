package com.github.johanneshaberlah.coronamonitor.daily;

import com.github.johanneshaberlah.coronamonitor.global.InfectionInformation;

public interface DailyInformationProvider {

  InfectionInformation provide(DailyInformationContext request);

}
