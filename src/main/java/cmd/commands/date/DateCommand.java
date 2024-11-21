package cmd.commands.date;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cmd.SimpleCmd;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(
        name = "date",
        description = "Retrieve last modified date",
        mixinStandardHelpOptions = true)
public class DateCommand implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(DateCommand.class);

    @Parameters(index = "0", description = "path of the file")
    private File file;


    @Override
    public void run() {

        File target = file;
        if (!file.exists()) {
            File cwd = SimpleCmd.getCurrentLocation();
            target = new File(cwd.getAbsolutePath(), file.getName());
        }
        if(!target.exists()) {
            LOG.info(target.getAbsolutePath());
            LOG.info("File does not exist.\n");
        } else if (target.exists() && target.isDirectory()) {
            LOG.info("Target is a directory\n");
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String dateString = dateFormat.format(new Date(target.lastModified()));
            LOG.info("Last modified: {} \n", dateString);
        }
    }
}
