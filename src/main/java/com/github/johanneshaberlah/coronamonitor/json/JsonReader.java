package com.github.johanneshaberlah.coronamonitor.json;

import com.google.gson.JsonElement;

import java.net.URL;

public interface JsonReader {

  JsonElement readJsonObject(URL url);

  JsonElement readJsonElement(String url, Object... parameter);

}
