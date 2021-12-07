package dev.abarmin.velosiped.checker.service;

import dev.abarmin.velosiped.checker.repository.ForksRepository;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Component
public class RepositoryTestsUpdater {
  @Autowired
  private RepositoryTestsLoader repositoryTestsLoader;

  @Autowired
  private ForksRepository forksRepository;

  public void update() {
    StreamSupport.stream(forksRepository.findAll().spliterator(), false)
        .map(repositoryTestsLoader::updateTests)
        .forEach(forksRepository::save);
  }
}
