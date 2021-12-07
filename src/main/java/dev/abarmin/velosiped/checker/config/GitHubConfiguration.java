package dev.abarmin.velosiped.checker.config;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aleksandr Barmin
 */
@Configuration
public class GitHubConfiguration {
  @Bean
  public GitHub gitHubClient(GitHubProperties credentials) throws Exception {
    if (StringUtils.isNoneBlank(credentials.getAccessToken())) {
      return new GitHubBuilder()
          .withOAuthToken(credentials.getAccessToken())
          .build();
    }

    return new GitHubBuilder()
        .withPassword(credentials.getUsername(), credentials.getPassword())
        .build();
  }
}
