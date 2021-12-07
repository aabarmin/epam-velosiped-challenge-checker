package dev.abarmin.velosiped.checker.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aleksandr Barmin
 */
@Configuration
public class XmlConfiguration {
  @Bean
  public XmlMapper xmlMapper() {
    return XmlMapper.builder()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .build();
  }
}
