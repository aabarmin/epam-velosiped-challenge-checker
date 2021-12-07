package dev.abarmin.velosiped.checker.service;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
@SpringJUnitConfig(classes = {
    RepositoryForkService.class,
    GitHubConfiguration.class,
    GitHubProperties.class
})
class RepositoryForkServiceTest {
  @Autowired
  private RepositoryForkService service;

  @Test
  void check_contextStarts() {
    assertNotNull(service);
  }

  @Test
  void check_listForksIsProvided() {
    final Set<String> forks = service.findForks("aabarmin/epam-java-cources");

    assertNotNull(forks);
  }
}