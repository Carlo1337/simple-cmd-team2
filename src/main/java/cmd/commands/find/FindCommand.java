package cmd.commands.find;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cmd.SimpleCmd;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/**
 * "Find Files" command class
 * <p/>
 * Executing the command returns a list files of a given dir, filtered by a file extension parameter
 */
@Command(
        name = "find",
        description = "find files in directory of type",
        mixinStandardHelpOptions = true)
public class FindCommand implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(FindCommand.class);

    @Parameters(index = "0", description = "file extension to look for")
    private String fileExtension;

    public FindCommand() {
        /* intentionally empty */
    }

    @Override
    public void run() {
        listFilesInDirectory(SimpleCmd.getCurrentLocation());
    }

    private void listFilesInDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (null != files) {
                List<File> list = Stream.of(files).sorted(getFileListComparator()).filter(f ->
                        f.getName().split("\\.").length >= 2 && f.getName().split("\\.")[1].equals(this.fileExtension.replace(".", ""))
                ).collect(Collectors.toList());
                if (!list.isEmpty()) {
                    list.forEach(this::printLine);
                } else {
                    LOG.info("No files found for extension {}\n", this.fileExtension);
                    LOG.info("To create a file, the command mkf can be used.\n");
                }
            }
        }
    }

    private Comparator<File> getFileListComparator() {
        return Comparator.comparing(File::getName, getSortOrderAwareFileNameComparator());
    }

    private Comparator<String> getSortOrderAwareFileNameComparator() {
        return Comparator.reverseOrder();
    }

    private void printLine(File f) {
        LOG.info("{}\n", f.getName());
    }
}
