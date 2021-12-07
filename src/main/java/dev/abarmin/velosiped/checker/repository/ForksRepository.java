package dev.abarmin.velosiped.checker.repository;

import dev.abarmin.velosiped.checker.domain.RepositoryFork;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Aleksandr Barmin
 */
public interface ForksRepository extends CrudRepository<RepositoryFork, Long> {
  Optional<RepositoryFork> findByName(String name);
}
