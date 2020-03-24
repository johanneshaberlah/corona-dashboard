package com.github.johanneshaberlah.coronamonitor.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class ConcurrentRefreshingSupplier<Type> extends RefreshingSupplier<Type> {
  private static final ExecutorService service = Executors.newCachedThreadPool();

  ConcurrentRefreshingSupplier(Supplier<Type> delegate, TimeAndUnit invalidationCycle) {
    super(delegate, invalidationCycle);
  }

  @Override
  public synchronized void invalidate() {
    service.execute(super::invalidate);
  }
}
