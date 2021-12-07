package dev.abarmin.velosiped.checker.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("REPOSITORY_FORK_TESTS")
public class RepositoryForkTest {
  @Id
  @Column("REPOSITORY_FORK_TEST_ID")
  private Long id;

  @Column("REPOSITORY_FORK_ID")
  private Long repositoryId;

  @Column("REPOSITORY_FORK_TEST_CLASS_NAME")
  private String className;

  @Column("REPOSITORY_FORK_TEST_CASES_TOTAL")
  private int testCasesTotal;

  @Column("REPOSITORY_FORK_TEST_CASES_SUCCESS")
  private int testCasesSuccess;
}
