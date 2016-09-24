package com.fieldaware;

import com.fieldaware.model.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * com.fieldaware.Main class, here is when the fun stuff begin
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

        if (0  == args.length || args[0].trim().isEmpty()) {
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
            Scanner scan = new Scanner(new File(args[0]));
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
