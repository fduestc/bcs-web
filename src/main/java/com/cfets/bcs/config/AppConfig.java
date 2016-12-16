package com.cfets.bcs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
// http://jackyrong.iteye.com/blog/2191818?utm_source=tuicool
@PropertySource(value = "classpath:app.properties")
public class AppConfig {
  // #全局配置
  @Value("${bcs.count}")
  private String bcsCount; // 1

  public AppConfig() {
  }

  // properties文件的占位符替换功能
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
    return new PropertySourcesPlaceholderConfigurer();
  }

// getters and setters
  public String getBcsCount() {
    return bcsCount;
  }

  public void setBcsCount(String bcsCount) {
    this.bcsCount = bcsCount;
  }


}
