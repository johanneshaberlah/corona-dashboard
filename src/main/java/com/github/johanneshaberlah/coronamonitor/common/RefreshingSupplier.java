package com.github.johanneshaberlah.coronamonitor.common;

import com.google.common.base.Preconditions;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class RefreshingSupplier<Type> implements Supplier<Type> {
  private static final ExecutorService service = Executors.newCachedThreadPool();

  private TimeAndUnit invalidationCycle;
  private Supplier<Type> delegate;

  private Type cachedReference;
  private long nextInvalidation;

  private RefreshingSupplier(Supplier<Type> delegate, TimeAndUnit invalidationCycle) {
    this.invalidationCycle = invalidationCycle;
    this.delegate = delegate;
    invalidate();
  }

  private void invalidate(){
    nextInvalidation = evaluateNextInvalidation();
    cachedReference = delegate.get();
  }

  private long evaluateNextInvalidation(){
    return invalidationCycle.addToUnixTime();
  }

  private boolean expired(){
    return System.currentTimeMillis() >= nextInvalidation;
  }

  @Override
  public Type get() {
    if (expired()){
      service.execute(this::invalidate);
    }
    return cachedReference;
  }

  public static <T>RefreshingSupplier<T> create(Supplier<T> delegate, TimeAndUnit invalidationCycle){
    Preconditions.checkNotNull(delegate);
    Preconditions.checkNotNull(invalidationCycle);
    return new RefreshingSupplier<T>(delegate, invalidationCycle);
  }
}
