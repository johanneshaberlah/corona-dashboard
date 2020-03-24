package com.github.johanneshaberlah.coronamonitor.common;

import java.util.function.Supplier;

public final class MoreSuppliers {

  public static <Type> Supplier<Type> concurrentRefreshingSupplier(Supplier<Type> delegate, TimeAndUnit invalidationCycle) {
    return new ConcurrentRefreshingSupplier<Type>(delegate, invalidationCycle);
  }

  public static <Type> Supplier<Type> refreshingSupplier(Supplier<Type> delegate, TimeAndUnit invalidationCycle) {
    return new RefreshingSupplier<Type>(delegate, invalidationCycle);
  }

}
