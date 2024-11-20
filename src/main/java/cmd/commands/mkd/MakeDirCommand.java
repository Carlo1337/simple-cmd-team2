package cmd.commands.mkd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@CommandLine.Command(
        name = "mkd",
        description = "Create a directory",
        mixinStandardHelpOptions = true)
public class MakeDirCommand implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(MakeDirCommand.class);

    @CommandLine.Parameters(index = "0", description = "path of the directory to create")
    private File directory;

    public MakeDirCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        createDirectory(directory.getAbsolutePath());
    }

    private void createDirectory(String dirName) {
        try {
            Path dirPath = Paths.get(dirName);
            Path createdPath = Files.createDirectory(dirPath);
            LOG.info("{} was created successfully\n", createdPath.toAbsolutePath());
        } catch (FileAlreadyExistsException e){
            LOG.info("Directory already exists: {}\n", directory.getAbsolutePath());
            LOG.info("Enter new directory: ");
            try (Scanner scanner = new Scanner(System.in)) {
                String nextLine = scanner.nextLine();
                if (!nextLine.isEmpty()) {
                    createDirectory(nextLine);
                }
            }
        } catch (Exception e) {
            LOG.info("Failed to create {}.\nReason: {}\n",
                    directory.getAbsolutePath(), e.getMessage());
        }
    }
}
