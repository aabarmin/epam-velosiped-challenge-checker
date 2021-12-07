package dev.abarmin.velosiped.checker.service.analysis;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Component
public class ArtifactsAnalyzer {
  @Autowired
  private XmlMapper xmlMapper;

  @SneakyThrows
  public Collection<AnalysisResults> analyse(Path reportFolder) {
    return Files.list(reportFolder)
        .filter(filePath -> !Files.isDirectory(filePath))
        .filter(filePath -> filePath.getFileName().toString().endsWith(".xml"))
        .map(this::process)
        .collect(Collectors.toList());
  }

  @SneakyThrows
  private AnalysisResults process(Path file) {
    final TestCase testCase = xmlMapper.readValue(file.toFile(), TestCase.class);
    return AnalysisResults.builder()
        .className(testCase.getName())
        .totalCases(testCase.getTests())
        .successCases(getSuccessCasesCount(testCase))
        .build();
  }

  private int getSuccessCasesCount(TestCase testCase) {
    return testCase.getTests() -
        testCase.getErrors() -
        testCase.getFailures();
  }
}
