package dev.abarmin.velosiped.checker.service;

import dev.abarmin.velosiped.checker.config.GitHubConfiguration;
import dev.abarmin.velosiped.checker.config.GitHubProperties;
import dev.abarmin.velosiped.checker.config.XmlConfiguration;
import dev.abarmin.velosiped.checker.domain.RepositoryFork;
import dev.abarmin.velosiped.checker.service.analysis.ArtifactsAnalyzer;
import dev.abarmin.velosiped.checker.service.download.ArtifactsDownloader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
@SpringJUnitConfig(classes = {
    RepositoryTestsLoader.class,
    GitHubConfiguration.class,
    ArtifactsDownloader.class,
    ArtifactsAnalyzer.class,
    XmlConfiguration.class,
    RepositoryTestsLoaderTest.ConfigurationForTest.class
})
@EnableConfigurationProperties(GitHubProperties.class)
@TestPropertySource(properties = {
    "github.access-token=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"
})
class RepositoryTestsLoaderTest {
  @Autowired
  private RepositoryTestsLoader tasksLoader;

  @Test
  void check_contextStarts() {
    assertNotNull(tasksLoader);
  }

  @Test
  @Disabled
  void loadWorkflows() {
    final RepositoryFork forkRepository = RepositoryFork.builder()
        .name("aabarmin/epam-velosiped-challenge-simple-public")
        .build();

    final RepositoryFork updatedRepository = tasksLoader.updateTests(forkRepository);

    assertNotNull(updatedRepository);
  }

  @Configuration
  static class ConfigurationForTest {
    @Bean
    public RestTemplate restTemplate() {
      return new RestTemplate();
    }
  }
}