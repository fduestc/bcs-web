package com.cfets.bcs.config;

import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.support.ResourcePropertySource;

public class AppConfigTest {

  @Test
  public void test() throws IOException {
    // name, location
    ResourcePropertySource propertySource2 = new ResourcePropertySource("resource", "classpath:app_dev.properties");

    System.out.println(propertySource2.getProperty("question.image.dir"));
  }
}
