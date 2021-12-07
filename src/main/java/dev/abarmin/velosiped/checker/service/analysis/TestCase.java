package dev.abarmin.velosiped.checker.service.analysis;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@JacksonXmlRootElement(localName = "testsuite")
public class TestCase {
  @JacksonXmlProperty
  private String name;
  @JacksonXmlProperty
  private int tests;
  @JacksonXmlProperty
  private int errors;
  @JacksonXmlProperty
  private int skipped;
  @JacksonXmlProperty
  private int failures;
}
