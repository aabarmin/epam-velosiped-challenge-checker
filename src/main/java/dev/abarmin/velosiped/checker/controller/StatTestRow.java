package dev.abarmin.velosiped.checker.controller;

import lombok.Builder;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
public class StatTestRow {
  private String className;
  private int testCases;
  private int testCasesSuccess;
}
