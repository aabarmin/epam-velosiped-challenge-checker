package dev.abarmin.velosiped.checker.service.download;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.kohsuke.github.GHArtifact;
import org.kohsuke.github.function.InputStreamFunction;
import org.springframework.stereotype.Component;

/**
 * This component is responsible for loading artefacts.
 *
 * @author Aleksandr Barmin
 */
@Component
public class ArtifactsDownloader {
  @SneakyThrows
  public Path downloadArtifacts(GHArtifact artifact) {
    return artifact.download(new Downloader());
  }

  private static class Downloader implements InputStreamFunction<Path> {
    @Override
    public Path apply(InputStream input) throws IOException {
      final Path targetFile = Files.createTempFile("artifacts_", ".zip");
      // downloading the file
      try (final OutputStream outputStream = Files.newOutputStream(targetFile)) {

        IOUtils.copy(input, outputStream);
      }

      // this should be a zip file, so, extracting it
      final Path targetDirectory = Files.createTempDirectory("artifacts_");
      try (final ZipInputStream inputStream = new ZipInputStream(Files.newInputStream(targetFile))) {
        ZipEntry nextEntry = inputStream.getNextEntry();
        while (nextEntry != null) {
          // extracting file
          final String originalFileName = nextEntry.getName();
          final Path unzippedFilePath = targetDirectory.resolve(originalFileName);

          try (final OutputStream outputStream = Files.newOutputStream(unzippedFilePath)) {
            IOUtils.copy(inputStream, outputStream);
          }

          nextEntry = inputStream.getNextEntry();
        }
      }

      return targetDirectory;
    }
  }
}
