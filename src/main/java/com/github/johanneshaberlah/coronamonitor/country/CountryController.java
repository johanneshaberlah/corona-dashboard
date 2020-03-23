package com.github.johanneshaberlah.coronamonitor.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public final class CountryController {
  private CountryModelView modelView;

  @Autowired
  private CountryController(CountryModelView modelView) {
    this.modelView = modelView;
  }

  @GetMapping("/")
  public String index(Model model) {
    modelView.applyValues(model);
    return "index";
  }

  @GetMapping("/country/{country}")
  public String index(Model model, @PathVariable Optional<Country> country) {
    country.ifPresent(model::addAttribute);
    return "country";
  }
}
