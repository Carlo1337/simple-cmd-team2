package cmd.commands.mkf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@CommandLine.Command(
        name = "mkf",
        description = "Create a file.",
        mixinStandardHelpOptions = true)
public class MakeFile implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(MakeFile.class);

    @Parameters(index = "0", description = "path of the file to create")
    private File file;


    @Override
    public void run() {

        try {
            Files.createFile(file.toPath());
        } catch (IOException e) {
            LOG.info("Datei existiert bereits.");
        }
    }
}
