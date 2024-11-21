package cmd.commands.copy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * "Copy File" command class
 * <p/>
 * Executing the command copies a file from one point to another.
 * If the file already exists in the target destination, the existing file is overwritten.
 */
@Command(
        name = "copy",
        description = "Copy a file",
        mixinStandardHelpOptions = true)
public class CopyCommand implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(CopyCommand.class);

    @Parameters(index = "0", description = "path of the file to copy")
    private File source;

    @Parameters(index = "1", description = "path to copy file to")
    private File target;

    public CopyCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        if(target.exists()) {
            LOG.info("{} already exists. Overwrite? (yes/no): ", target.getAbsolutePath());
            try (Scanner scanner = new Scanner(System.in)) {
                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes")) {
                    copy(source.toPath(), target.toPath());
                } else if (response.equals("no")) {
                    LOG.info("Copy canceled.\n");
                }
            } catch (Exception e) {
                LOG.error("Error", e);
            }
        } else {
            copy(source.toPath(), target.toPath());
        }
    }

    private void copy(Path source, Path target) {
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            LOG.info("{} copied successfully.\n", target.toAbsolutePath());
        } catch (Exception e) {
            LOG.info("Error copying the file {}: \n", source.toAbsolutePath());
        }
    }
}
