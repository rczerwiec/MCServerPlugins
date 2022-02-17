package com.stylowamc.smcoin.FileLoggers;

import com.stylowamc.smcoin.SMCoin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PaymentsLogToFile {
    public void logToFile(String message) {
        SMCoin plugin = SMCoin.getInstance();

        try {
            File dataFolder = plugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File saveTo = new File(dataFolder, "data.txt");
            if (!saveTo.exists()) {
                saveTo.createNewFile();
            }

            FileWriter fw = new FileWriter(saveTo, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(message);
            pw.flush();
            pw.close();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }
}
