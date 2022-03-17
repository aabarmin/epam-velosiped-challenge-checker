package dev.abarmin.velosiped.checker.service;

import dev.abarmin.velosiped.checker.domain.RepositoryFork;
import dev.abarmin.velosiped.checker.repository.ForksRepository;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Slf4j
@Component
public class RepositoryTestsUpdater {
  @Autowired
  private RepositoryTestsLoader repositoryTestsLoader;

  @Autowired
  private ForksRepository forksRepository;

  public void update() {
    StreamSupport.stream(forksRepository.findAll().spliterator(), false)
        .map(this::updateAndHandleExceptions)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(forksRepository::save);
  }

  private Optional<RepositoryFork> updateAndHandleExceptions(final RepositoryFork fork) {
    try {
      final RepositoryFork updatedFork = repositoryTestsLoader.updateTests(fork);
      return Optional.of(updatedFork);
    } catch (Exception e) {
      log.error("Can't update fork {}, error: ", fork.getName(), e);
      return Optional.empty();
    }
  }
}
