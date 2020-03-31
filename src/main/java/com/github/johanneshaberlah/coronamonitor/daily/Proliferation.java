package com.github.johanneshaberlah.coronamonitor.daily;

import com.github.johanneshaberlah.coronamonitor.global.InfectionInformation;

public final class Proliferation {
  private long absolute;
  private double relative;

  private Proliferation(long absolute, double relative) {
    this.absolute = absolute;
    this.relative = relative;
  }

  public long absolute() {
    return absolute;
  }

  public double relative() {
    return relative;
  }

  public static Proliferation calculate(InfectionInformation left, InfectionInformation right){
    long absolute = right.confirmed() - left.confirmed();
    double relative = 1 - ((double) right.confirmed() / (double) left.confirmed());
    return create(absolute, relative);
  }

  public static Proliferation create(long absolute, double relative) {
    return new Proliferation(absolute, relative);
  }
}
