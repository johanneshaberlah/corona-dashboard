package com.github.johanneshaberlah.coronamonitor.json;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Primary
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public final class CachedJsonReader implements JsonReader {
  private JsonReader delegate;
  private LoadingCache<URL, JsonElement> cache;

  @Autowired
  private CachedJsonReader(JsonReader delegate) {
    this.delegate = delegate;
    this.cache = createCache();
  }

  @Override
  public JsonElement readJsonObject(URL url) {
    return cache.getUnchecked(url);
  }

  public JsonElement readJsonElement(String url, Object... parameter) {
    try {
      return readJsonObject(new URL(String.format(url, parameter)));
    } catch (MalformedURLException ignored) {
      return null;
    }
  }

  private LoadingCache<URL, JsonElement> createCache() {
    return CacheBuilder.newBuilder()
      .expireAfterAccess(25, TimeUnit.MINUTES)
      .build(createDelegatingCacheLoader());
  }

  private CacheLoader<URL, JsonElement> createDelegatingCacheLoader() {
    return new CacheLoader<URL, JsonElement>() {
      @Override
      public JsonElement load(URL url) {
        return delegate.readJsonObject(url);
      }
    };
  }
}
