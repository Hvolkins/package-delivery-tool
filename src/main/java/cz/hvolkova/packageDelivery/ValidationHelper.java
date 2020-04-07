package cz.hvolkova.packageDelivery;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Validation of package, not yet work
 *
 * @author Elena Hvolkova
 */
public class ValidationHelper {
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("[0-9]+(\\.){0,3}[0-9]*");
    private static final Pattern INTEGER_PATTERN = Pattern.compile("\\d{5}");

    public PostalPackage valid(Scanner scanner) {
        Double weight = null;
        Integer postalCode = null;

        List<String> fields = readDataFromScanner(scanner);

        if (null != fields && !fields.isEmpty()) {
            String strWeight = fields.get(0).trim();
            String strPostalCode = fields.get(1).trim();

            if (null != strWeight) {
                Matcher m = DOUBLE_PATTERN.matcher(strWeight);
                while(!m.find()) {
                    System.out.println("Please enter a positive double number for weight! For example 56.123");
                    strWeight = scanner.next();
                }
                String w = m.group();
            }

            if (null != strPostalCode) {
                Matcher m = INTEGER_PATTERN.matcher(strPostalCode);
                while(!m.find()){
                    System.out.println("Please enter a positive number (5 number) for postal code! For example 41501");
                    strPostalCode = scanner.next();
                }
                String p = m.group();
            }
            weight = Double.valueOf(strWeight);
            postalCode = Integer.valueOf(strPostalCode);
        }
        return new PostalPackage(weight, postalCode);
    }

    private List<String> readDataFromScanner(Scanner scanner) {
        String line = scanner.nextLine();   // read the next line
        return Stream.of(line.split(" ", -1)).collect(Collectors.toList());
    }


    public PostalPackage validation(Scanner scanner) {
        Double weight = null;
        Integer postalCode = null;

        while (!scanner.hasNext(DOUBLE_PATTERN)) {
            System.out.println("Please enter a positive double number for weight! For example 56.123");
            scanner.next();
            // Read second parameter and ignore
            String pc = scanner.next();
            // Read first parameter from new entered line
            weight = scanner.nextDouble();
        }
        if (scanner.hasNext(DOUBLE_PATTERN))
            weight = scanner.nextDouble();


        while (!scanner.hasNext(INTEGER_PATTERN)) {
            System.out.println("Please enter a positive number (5 digits) for postal code! For example 41501");
            scanner.next();
            // Read first parameter and ignore
            String w = scanner.next();
            postalCode = scanner.nextInt();
        }
        if (scanner.hasNext(INTEGER_PATTERN))
            postalCode = scanner.nextInt();
        return new PostalPackage(weight, postalCode);
    }
}
