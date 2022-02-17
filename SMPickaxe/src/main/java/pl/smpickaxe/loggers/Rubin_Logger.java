package pl.smpickaxe.loggers;

import pl.smpickaxe.Smpickaxe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Rubin_Logger {
    public void logToFile(String message)
    {
        Smpickaxe plugin = Smpickaxe.getInstance();
        try
        {
            File dataFolder = plugin.getDataFolder();
            if(!dataFolder.exists())
            {
                dataFolder.mkdir();
            }

            File saveTo = new File(dataFolder, "rubin_logs.txt");
            if (!saveTo.exists())
            {
                saveTo.createNewFile();
            }


            FileWriter fw = new FileWriter(saveTo, true);

            PrintWriter pw = new PrintWriter(fw);

            pw.println(message);

            pw.flush();

            pw.close();

        } catch (IOException e)
        {

            e.printStackTrace();

        }

    }
}
