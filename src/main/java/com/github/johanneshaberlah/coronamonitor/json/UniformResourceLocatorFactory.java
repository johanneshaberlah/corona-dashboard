package com.github.johanneshaberlah.coronamonitor.json;

import java.net.MalformedURLException;
import java.net.URL;

public final class UniformResourceLocatorFactory {

  public static URL create(String path){
    try {
      return new URL(path);
    } catch (MalformedURLException failure) {
      failure.printStackTrace();
    }
    return null;
  }
}
