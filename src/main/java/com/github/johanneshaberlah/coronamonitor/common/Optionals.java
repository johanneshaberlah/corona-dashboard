package com.github.johanneshaberlah.coronamonitor.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import java.util.Arrays;
import java.util.Optional;

public final class Optionals {

  public static Optional<JsonElement> ofNullableJsonElement(JsonElement element) {
    if (element == JsonNull.INSTANCE) {
      return Optional.empty();
    }
    return Optional.ofNullable(element);
  }

  public static <Type> Optional<Type> presentOptional(Optional<Type>... optionals) {
    return Arrays.stream(optionals)
      .filter(Optional::isPresent)
      .findFirst()
      .orElse(Optional.empty());
  }
}
