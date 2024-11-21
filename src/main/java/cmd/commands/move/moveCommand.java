package cmd.commands.move;

import cmd.SimpleCmd;
import cmd.commands.mkd.MakeDirCommand;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;

/**
 * This Class implements the change Directory functionality
 */

@CommandLine.Command(
        name = "move",
        description = "Moves File to another Directory and creates Dir if not existent",
        mixinStandardHelpOptions = true)
public class moveCommand implements Runnable {

    @CommandLine.Parameters(index = "0", description = "from Location")
    private File source;
    @CommandLine.Parameters(index = "1", description = "path to move file to")
    private File target;

    public moveCommand() {
        /*empty*/
    }

    public void run() {
        try {
            Files.move(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            try {
                String path = target.getParent();
                Files.createDirectory(Path.of(path));
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
}
