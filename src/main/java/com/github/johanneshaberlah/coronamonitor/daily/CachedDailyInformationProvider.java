package com.github.johanneshaberlah.coronamonitor.daily;

import com.github.johanneshaberlah.coronamonitor.global.InfectionInformation;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Primary
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class CachedDailyInformationProvider implements DailyInformationProvider {
  private DailyInformationProvider delegate;
  private LoadingCache<DailyInformationContext, InfectionInformation> cache;

  @Autowired
  private CachedDailyInformationProvider(DailyInformationProvider delegate) {
    this.delegate = delegate;
    cache = createCache();
  }

  @Override
  public InfectionInformation provide(DailyInformationContext request) {
    return cache.getUnchecked(request);
  }

  private LoadingCache<DailyInformationContext, InfectionInformation> createCache() {
    return CacheBuilder.newBuilder()
      .expireAfterAccess(25, TimeUnit.MINUTES)
      .build(createDelegatingCacheLoader());
  }

  private CacheLoader<DailyInformationContext, InfectionInformation> createDelegatingCacheLoader() {
    return new CacheLoader<DailyInformationContext, InfectionInformation>() {
      @Override
      public InfectionInformation load(DailyInformationContext request) {
        return delegate.provide(request);
      }
    };
  }
}
