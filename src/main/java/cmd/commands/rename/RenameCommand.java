package cmd.commands.rename;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
import java.nio.file.*;
import java.util.Scanner;

@CommandLine.Command(
        name = "rename",
        description = "Rename Files",
        mixinStandardHelpOptions = true)
public class RenameCommand implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(RenameCommand.class);

    @CommandLine.Parameters(index = "0", description = "original file path")
    private File source;

    @CommandLine.Parameters(index = "1", description = "new file path")
    private File target;

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            if(target.exists()) {
                LOG.info("{} already exists. Overwrite? (yes/no): ", target.getAbsolutePath());
                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes")) {
                    renameFile(source.toPath(), target.toPath());
                } else if (response.equals("no")) {
                    LOG.info("Renaming canceled.\n");
                }
            } else {
                renameFile(source.toPath(), target.toPath());
            }
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    private void renameFile(Path source, Path target) {
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            LOG.info("{} renamed successfully.\n", target.toAbsolutePath());
        } catch (Exception e) {
            LOG.info("Error renaming the file {}: \n", source.toAbsolutePath());
        }
    }
}
