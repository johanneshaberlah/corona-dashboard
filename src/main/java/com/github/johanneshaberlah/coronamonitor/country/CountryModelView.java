package com.github.johanneshaberlah.coronamonitor.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public final class CountryModelView {
  private static final String TOP_COUNTRY_ATTRIBUTE = "topCountries";
  private static final String GLOBAL_INFORMATION_ATTRIBUTE = "global";
  private static final Integer TOP_COUNTRY_LIMIT = 20;

  private CountryModel countryModel;

  @Autowired
  private CountryModelView(CountryModel countryModel) {
    this.countryModel = countryModel;
  }

  public void applyValues(Model model) {
    addGlobalInformationAttribute(model);
    addTopCountryAttribute(model);
  }

  private void addGlobalInformationAttribute(Model model) {
    model.addAttribute(GLOBAL_INFORMATION_ATTRIBUTE, countryModel.globalInfectionInformation());
  }

  private void addTopCountryAttribute(Model model) {
    model.addAttribute(TOP_COUNTRY_ATTRIBUTE, countryModel.findMostInfectedCountries(TOP_COUNTRY_LIMIT));
  }
}
