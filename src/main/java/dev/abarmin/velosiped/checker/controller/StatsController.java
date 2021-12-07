package dev.abarmin.velosiped.checker.controller;

import dev.abarmin.velosiped.checker.domain.RepositoryFork;
import dev.abarmin.velosiped.checker.domain.RepositoryForkTest;
import dev.abarmin.velosiped.checker.repository.ForksRepository;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Aleksandr Barmin
 */
@Controller
@RequestMapping("/")
public class StatsController {
  @Autowired
  private ForksRepository forksRepository;

  @GetMapping("")
  public ModelAndView index(ModelAndView modelAndView) {
    modelAndView.setViewName("stats/index");
    modelAndView.addObject("items", toRows(forksRepository.findAll()));
    return modelAndView;
  }

  private Collection<StatRow> toRows(Iterable<RepositoryFork> forks) {
    return StreamSupport.stream(forks.spliterator(), false)
        .map(this::toRow)
        .sorted(Comparator.comparing(StatRow::getTotalTestsSuccess))
        .collect(Collectors.toList());
  }

  private StatRow toRow(RepositoryFork fork) {
    return StatRow.builder()
        .repository(fork.getName())
        .updated(fork.getLastUpdated().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
        .totalTests(totalTests(fork))
        .totalTestsSuccess(successTests(fork))
        .tests(toTests(fork.getTests()))
        .build();
  }

  private Collection<StatTestRow> toTests(Set<RepositoryForkTest> tests) {
    return tests.stream()
        .map(this::toTest)
        .sorted(Comparator.comparing(StatTestRow::getClassName))
        .collect(Collectors.toList());
  }

  private StatTestRow toTest(RepositoryForkTest test) {
    return StatTestRow.builder()
        .className(test.getClassName())
        .testCases(test.getTestCasesTotal())
        .testCasesSuccess(test.getTestCasesSuccess())
        .build();
  }

  private int successTests(RepositoryFork fork) {
    int result = 0;
    for (RepositoryForkTest test : fork.getTests()) {
      result += test.getTestCasesSuccess();
    }
    return result;
  }

  private int totalTests(RepositoryFork fork) {
    int result = 0;
    for (RepositoryForkTest test : fork.getTests()) {
      result += test.getTestCasesTotal();
    }
    return result;
  }
}
