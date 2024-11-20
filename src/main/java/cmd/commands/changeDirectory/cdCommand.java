package cmd.commands.changeDirectory;

import cmd.SimpleCmd;
import picocli.CommandLine;

import java.io.File;

/**
 * This Class implements the change Directory functionality
 */

@CommandLine.Command(
        name = "cd",
        description = "Changes Directory",
        mixinStandardHelpOptions = true)
public class cdCommand implements Runnable {

    @CommandLine.Parameters(index = "0", description = "where to change to")
    private File file;

    public cdCommand() {
        /*empty*/
    }

    public void run() {
        changeDirectory(file);
    }

    void changeDirectory(File directory) {
        SimpleCmd.setCurrentLocation(directory);
        System.out.println(directory.getAbsolutePath());
    }
}
