package dev.abarmin.velosiped.checker.service;

import dev.abarmin.velosiped.checker.domain.RepositoryFork;
import dev.abarmin.velosiped.checker.repository.ForksRepository;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This component is responsible for keeping the list of forks up-to-date.
 *
 * @author Aleksandr Barmin
 */
@Slf4j
@Component
public class RepositoryForkLoader {
  @Autowired
  private RepositoryForkService service;

  @Autowired
  private ForksRepository repository;

  @Autowired
  private GitHubProperties properties;

  public void load() {
    final Set<String> forks = service.findForks(properties.getMainRepository());
    for (String fork : forks) {
      if (repository.findByName(fork).isEmpty()) {
        // creating a new repository
        log.info("Creating a new record for repo {}", fork);
        final RepositoryFork newRepository = RepositoryFork.builder()
            .name(fork)
            .lastUpdated(LocalDateTime.now())
            .build();
        repository.save(newRepository);
      } else {
        log.info("For with name {} exists, skipping", fork);
      }
    }
  }
}
