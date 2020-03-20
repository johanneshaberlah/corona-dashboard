package com.github.johanneshaberlah.coronamonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class CoronaMonitorApplication {

  public static void main(String[] options) {
    SpringApplication.run(CoronaMonitorApplication.class, options);
  }
}
