package com.fieldaware;

import com.fieldaware.model.Log;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Main class, here is when the fun stuff begin
 *
 * @author saul.martinez
 */
public class Main {

    /**
     * Process the input log file
     *
     * @param args
     */
    public static void main(String[] args) {

        // validate the file name received as parameter one
        if (0 == args.length || args[0].trim().isEmpty()) {
            String jarName = new File(Log.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath()).getName();
            System.err.println("Filename must be specified as the first parameter");
            System.err.println("Examples:");
            System.err.println("java -jar " + jarName + " /var/log/test.log");
            System.err.println("java -jar " + jarName + " \"C:\\Users\\fieldaware logs\\test.log\"");
            System.exit(1);
        }

        try {

            // checks if the profile flag is present
            boolean enableProfiling = (2 == args.length && args[1].trim().equals("--profile"));
            Analyzer analyzer = new Analyzer(args[0], enableProfiling);
            System.out.println(analyzer.entries.size() + " lines processed");
            if (enableProfiling) {
                System.out.println(analyzer.profiler);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
