package dev.abarmin.velosiped.checker.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Aleksandr Barmin
 */
@Service
public class RepositoryForkService {
  @Autowired
  private GitHub gitHub;

  @SneakyThrows
  public Set<String> findForks(final String mainRepository) {
    final List<GHRepository> allForks = gitHub.getRepository(mainRepository)
        .listForks()
        .toList();
    return allForks.stream()
        .map(repository -> repository.getFullName())
        .collect(Collectors.toSet());
  }
}
