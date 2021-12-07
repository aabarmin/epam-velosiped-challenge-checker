package dev.abarmin.velosiped.checker.domain;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("REPOSITORY_FORKS")
public class RepositoryFork {
  @Id
  @Column("REPOSITORY_FORK_ID")
  private Long id;

  @Column("REPOSITORY_FORK_NAME")
  private String name;

  @Column("REPOSITORY_FORK_LAST_UPDATED")
  private LocalDateTime lastUpdated;

  @MappedCollection(idColumn = "REPOSITORY_FORK_TEST_ID", keyColumn = "REPOSITORY_FORK_ID")
  private Set<RepositoryForkTest> tests;
}
