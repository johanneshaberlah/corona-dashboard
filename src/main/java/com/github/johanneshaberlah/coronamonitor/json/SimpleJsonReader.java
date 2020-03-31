package com.github.johanneshaberlah.coronamonitor.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
public final class SimpleJsonReader implements JsonReader {
  private static final Charset CHARSET = StandardCharsets.UTF_8;

  private final JsonParser parser = new JsonParser();

  @Override
  public JsonElement readJsonObject(URL url) {
    try {
      return parser.parse(IOUtils.toString(url, CHARSET));
    } catch (IOException ignored) {
      // Ignored
    }
    return JsonNull.INSTANCE;
  }

  public JsonElement readJsonElement(String url, Object... parameter) {
    try {
      return readJsonObject(new URL(String.format(url, parameter)));
    } catch (MalformedURLException ignored) {
      return null;
    }
  }
}
