package cmd.commands.mkf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

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
            // Informiere den Nutzer, wenn die Datei bereits existiert
            LOG.info("Datei existiert bereits. Geben Sie einen neuen Namen ein!\n");
            try (Scanner scanner = new Scanner(System.in)) {
                String nextLine = scanner.nextLine();
                // Alternativer Datei-Name
                if (!nextLine.isEmpty()) {
                    Files.createFile(Path.of(nextLine));
                }
            } catch (IOException ex) {
                LOG.error("Konnte Datei nicht erstellen");
            }
        }
    }
}
