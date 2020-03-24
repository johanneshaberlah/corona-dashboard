package com.github.johanneshaberlah.coronamonitor.country;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class CountryFactory {
  private static final String NAME_ATTRIBUTE = "name";
  private static final String SHORTCUT_STYLE = "iso2";
  private static final String ISO_STYLE = "iso3";

  public Country create(String name, String iso, String shortcut) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(iso);
    Preconditions.checkNotNull(shortcut);
    return new Country(name, iso, shortcut);
  }

  public Country of(JsonObject object) {
    if (!object.has(SHORTCUT_STYLE)) {
      return create(object.get(NAME_ATTRIBUTE).getAsString(), "", "");
    }
    return create(
      object.get(NAME_ATTRIBUTE).getAsString(),
      object.get(SHORTCUT_STYLE).getAsString(),
      object.get(ISO_STYLE).getAsString()
    );
  }
}
