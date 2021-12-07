package dev.abarmin.velosiped.checker.service;

import dev.abarmin.velosiped.checker.domain.RepositoryFork;
import dev.abarmin.velosiped.checker.domain.RepositoryForkTest;
import dev.abarmin.velosiped.checker.service.analysis.AnalysisResults;
import dev.abarmin.velosiped.checker.service.analysis.ArtifactsAnalyzer;
import dev.abarmin.velosiped.checker.service.download.ArtifactsDownloader;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.github.GHArtifact;
import org.kohsuke.github.GHWorkflow;
import org.kohsuke.github.GHWorkflowRun;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This component updates status of tests.
 *
 * @author Aleksandr Barmin
 */
@Slf4j
@Component
public class RepositoryTestsLoader {
  @Autowired
  private GitHub gitHub;

  @Autowired
  private ArtifactsDownloader artifactsDownloader;

  @Autowired
  private ArtifactsAnalyzer artifactsAnalyzer;

  @SneakyThrows
  public RepositoryFork updateTests(final RepositoryFork toUpdate) {
    log.info("Checking tests for {}", toUpdate.getName());
    final Optional<GHArtifact> artifactOptional = findSuitableArtifact(toUpdate);
    if (artifactOptional.isEmpty()) {
      log.info("No suitable artifact for downloading");
      return toUpdate;
    }

    final Path downloadedArtifactsPath = artifactsDownloader.downloadArtifacts(artifactOptional.get());
    log.info("Downloaded");

    log.info("Analyzing the reports");
    final Collection<AnalysisResults> analysisResults = artifactsAnalyzer.analyse(downloadedArtifactsPath);
    log.info("Analyzed");

    toUpdate.setLastUpdated(LocalDateTime.now());
    toUpdate.setTests(toTests(analysisResults, toUpdate));

    return toUpdate;
  }

  @SneakyThrows
  private Optional<GHArtifact> findSuitableArtifact(final RepositoryFork toUpdate) {
    final Optional<GHWorkflow> workflowOptional = gitHub.getRepository(toUpdate.getName())
        .listWorkflows()
        .toList()
        .stream()
        .filter(wf -> StringUtils.equalsIgnoreCase(wf.getName(), "Java CI with Maven"))
        .findFirst();
    if (workflowOptional.isEmpty()) {
      log.info("No configured workflows for repo {}", toUpdate.getName());
      return Optional.empty();
    }
    final GHWorkflow workflow = workflowOptional.get();
    // taking the latest run
    final Optional<GHWorkflowRun> latestRunOptional = workflow.listRuns()
        .toList()
        .stream()
        .sorted(Comparator.comparing(GHWorkflowRun::getWorkflowId))
        .findFirst();
    if (latestRunOptional.isEmpty()) {
      log.info("No runs found, skipping for now");
      return Optional.empty();
    }
    final GHWorkflowRun latestRun = latestRunOptional.get();
    log.info("Downloading artifacts for {}", toUpdate.getName());
    return latestRun.listArtifacts()
        .toList()
        .stream()
        .filter(art -> StringUtils.equalsIgnoreCase(art.getName(), "test-results"))
        .findFirst();
  }

  private Set<RepositoryForkTest> toTests(Collection<AnalysisResults> analysisResults, RepositoryFork toUpdate) {
    return analysisResults.stream()
        .map(result -> RepositoryForkTest.builder()
            .repositoryId(toUpdate.getId())
            .className(result.getClassName())
            .testCasesTotal(result.getTotalCases())
            .testCasesSuccess(result.getSuccessCases())
            .build())
        .collect(Collectors.toSet());
  }
}
