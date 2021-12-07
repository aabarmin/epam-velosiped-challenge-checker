package dev.abarmin.velosiped.checker.controller;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
public class StatRow {
  private String repository;
  private String updated;
  private int totalTests;
  private int totalTestsSuccess;
  @Builder.Default
  private Collection<StatTestRow> tests = new ArrayList<>();
}
