package dev.abarmin.velosiped.checker.service.analysis;

import lombok.Builder;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
public class AnalysisResults {
  private String className;
  private int totalCases;
  private int successCases;
}
