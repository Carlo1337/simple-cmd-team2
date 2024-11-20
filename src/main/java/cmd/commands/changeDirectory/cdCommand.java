package cmd.commands.changeDirectory;

import cmd.SimpleCmd;
import picocli.CommandLine;

import java.io.File;
import java.util.Scanner;
import java.util.stream.Stream;

@CommandLine.Command(
        name = "cd",
        description = "Changes Directory",
        mixinStandardHelpOptions = true)
public class cdCommand implements Runnable {

    private File file;

    public cdCommand() {
        /*empty*/
    }

    public void run() {
        String directory = "";

        Scanner scanner = new Scanner(System.in);
        directory = scanner.nextLine();

        File file = new File(directory);

        changeDirectory(file);

        scanner.close();
    }

    void changeDirectory(File directory) {
        SimpleCmd.setCurrentLocation(directory);
        System.out.println(directory.getAbsolutePath());
    }
}
