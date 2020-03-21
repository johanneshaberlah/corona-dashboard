package com.github.johanneshaberlah.coronamonitor.common;

import com.google.common.base.Preconditions;

import java.util.concurrent.TimeUnit;

public final class TimeAndUnit {
  private TimeUnit unit;
  private long time;

  private TimeAndUnit(TimeUnit unit, long time) {
    this.unit = unit;
    this.time = time;
  }

  public TimeUnit unit() {
    return unit;
  }

  public long time() {
    return time;
  }

  public long addToUnixTime(){
    return System.currentTimeMillis() + (unit.toMillis(time));
  }

  public static TimeAndUnit create(TimeUnit unit, long time){
    Preconditions.checkNotNull(unit);
    return new TimeAndUnit(unit, time);
  }
}
