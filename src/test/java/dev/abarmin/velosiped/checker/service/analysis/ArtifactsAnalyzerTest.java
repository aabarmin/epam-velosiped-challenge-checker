package dev.abarmin.velosiped.checker.service.analysis;

import dev.abarmin.velosiped.checker.service.XmlConfiguration;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
@SpringJUnitConfig(classes = {
    ArtifactsAnalyzer.class,
    XmlConfiguration.class
})
class ArtifactsAnalyzerTest {
  @Autowired
  private ArtifactsAnalyzer analyzer;

  @Value("classpath:examples")
  private Resource resource;

  @Test
  void check_contextStarts() {
    assertNotNull(analyzer);
  }

  @Test
  void analyze_testFolder() throws Exception {
    final Collection<AnalysisResults> result = analyzer.analyse(resource.getFile().toPath());

    assertNotNull(result);
  }
}