package cz.hvolkova.packageDelivery;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Util for importing data from text file over console
 *
 * @author Elena Hvolková
 */
public class FileUtil {
    private static final String FILE_INPUT = "/target/resources/input.txt";
    private static final String FILE_FEES = "fees.txt";

    /**
     * Import initial data about packages.
     *
     * @param scanner
     * @return list of
     * @see PostalPackage
     */
    public List<PostalPackage> readInitialFile(Scanner scanner) {
        System.out.println("Enter the file name with path:");
        String name = scanner.nextLine();

        File file = new File(name);
        System.out.println("File exist - " + file.exists());

        List<PostalPackage> list = new ArrayList<>();

        // check that the file exists
        if (file.exists()) {
            // Create a Scanner from the file.
            Scanner inFile = null;
            try {
                inFile = new Scanner(file);
                while (inFile.hasNext()) {
                    String line = inFile.nextLine();   // read the next line
                    // Writing elements from line into list
                    List<String> fields = Stream.of(line.split(" ", -1)).collect(Collectors.toList());
                    if (fields != null && !fields.isEmpty()) {
                        // Creating new package with data read from file
                        PostalPackage pcg = new PostalPackage(Double.valueOf(fields.get(0).trim()), Integer.valueOf(fields.get(1).trim()));
                        list.add(pcg);
                    }
                }
                System.out.println("Imported data from file. ");
                // Close the Scanner object attached to the file
                inFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * Import data about fees.
     *
     * @param scanner
     * @return list of
     * @see Fee
     */
    public List<Fee> readFeesFromFile(Scanner scanner) {
        System.out.println("Enter the file name with path:");
        String name = scanner.nextLine();

        File file = new File(name);
        System.out.println("File exist - " + file.exists());

        List<Fee> list = new ArrayList<>();

        if (file.exists()) {
            Scanner inFile = null;
            try {
                inFile = new Scanner(file);
                while (inFile.hasNext()) {
                    String line = inFile.nextLine();   // read the next line
                    List<String> fields = Stream.of(line.split(" ", -1)).collect(Collectors.toList());
                    if (fields != null && !fields.isEmpty()) {
                        Fee fee = new Fee(Double.valueOf(fields.get(0).trim()), Double.valueOf(fields.get(1).trim()));
                        list.add(fee);
                    }
                }
                System.out.println("Imported fees from file. ");
                inFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        return list;
    }
}
