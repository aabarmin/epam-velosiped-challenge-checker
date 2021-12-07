package dev.abarmin.velosiped.checker;

import dev.abarmin.velosiped.checker.config.GitHubProperties;
import dev.abarmin.velosiped.checker.service.RepositoryForkLoader;
import dev.abarmin.velosiped.checker.service.RepositoryTestsUpdater;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(GitHubProperties.class)
public class CheckerApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(CheckerApplication.class, args);
	}

	@Autowired
	private RepositoryForkLoader forkLoader;

	@Autowired
	private RepositoryTestsUpdater testsUpdater;

	@Override
	public void run(ApplicationArguments args) {
		loadForks();
	}

	@Scheduled(fixedDelay = 60, initialDelay = 10, timeUnit = TimeUnit.SECONDS)
	public void loadForks() {
		log.info("Running initial load of forks");
		forkLoader.load();
		log.info("Done");

		log.info("Running initial test updates");
		testsUpdater.update();
		log.info("Done");
	}
}
