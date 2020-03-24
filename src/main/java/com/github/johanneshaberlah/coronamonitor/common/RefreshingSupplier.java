package com.github.johanneshaberlah.coronamonitor.common;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class RefreshingSupplier<Type> implements Supplier<Type> {
  private final Lock lock = new ReentrantLock();

  private TimeAndUnit invalidationCycle;
  private Supplier<Type> delegate;

  private Type cachedReference;
  private long nextInvalidation;

  RefreshingSupplier(Supplier<Type> delegate, TimeAndUnit invalidationCycle) {
    this.invalidationCycle = invalidationCycle;
    this.delegate = delegate;
  }

  public void invalidate() {
    Stopwatch stopwatch = Stopwatch.createStarted();
    nextInvalidation = evaluateNextInvalidation();
    cachedReference = delegate.get();
    System.out.println(String.format("Cache Invalidation succeed [%sms]", stopwatch.elapsed(TimeUnit.MILLISECONDS)));
  }

  private long evaluateNextInvalidation() {
    return invalidationCycle.addToUnixTime();
  }

  private boolean expired() {
    return System.currentTimeMillis() >= nextInvalidation;
  }

  @Override
  public Type get() {
    if (expired()) {
      invalidate();
    }
    return cachedReference;
  }
}
