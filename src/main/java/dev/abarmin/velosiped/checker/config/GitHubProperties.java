package dev.abarmin.velosiped.checker.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Aleksandr Barmin
 */
@Data
@ConfigurationProperties(prefix = "github")
public class GitHubProperties {
  private String username;
  private String password;
  private String accessToken;
  private String mainRepository;
}
