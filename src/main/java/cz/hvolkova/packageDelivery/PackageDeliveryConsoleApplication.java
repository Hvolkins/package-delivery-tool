package cz.hvolkova.packageDelivery;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The PackageDelivery program implements work with package
 * @see PostalPackage from console (input/output data).
 *
 * Is possible:
 * 1) added data manualy,
 * 	  format (<weight: positive number, >0, maximal 3 decimal places, . (dot) as decimal separator><space><postal code: fixed 5 digits> )
 * 2) import packages from text file
 * 3) import fees from text file, recount output
 * 4) quit from program
 * Use menu after PackageDelivery tool starting for help information.
 *
 * @author Elena Hvolkova
 * @version 1.0
 * @since 2020-07-04
 */
@EnableScheduling
@SpringBootApplication
public class PackageDeliveryConsoleApplication implements CommandLineRunner {
	private PostalPackageService packageService;

	private final ValidationHelper helper = new ValidationHelper();
	private final FileUtil fileUtil = new FileUtil();

	public PackageDeliveryConsoleApplication(final PostalPackageService packageService) {
		this.packageService = packageService;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PackageDeliveryConsoleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		setHelpMenu();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			String inputArgument = scanner.nextLine().trim();

			// Control if has entered parameter in console
			if (StringUtils.isEmpty(inputArgument))
				System.out.println("Enter some parameter in console..");
			else {
				// Find parameter in enumeration. If exists, use for working with console menu
				ConsoleParameterEnum argument = findArgumentByValue(inputArgument);
				if (null != argument) {
					switch (argument) {
						case NEW_PCG:
						case NEW_PCG_SHORT: {
							// Saving manually added package
							addNewPackage(scanner);
							break;
						}
						case FILE_INITIAL:
						case FILE_INITIAL_SHORT: {
							// Read information about packages
							List<PostalPackage> data = fileUtil.readInitialFile(scanner);
							// Saving data in memory
							packageService.saveFromFile(data);
							break;
						}
						case FILE_FEES:
						case FILE_FEES_SHORT: {
							// Read information about fees
							List<Fee> data = fileUtil.readFeesFromFile(scanner);
							// Added fee to existed in memory packages
							packageService.recountFeeForPackages(data);
							break;
						}
						case QUIT:
						case QUIT_SHORT: {
							System.out.println("Exiting from Package delivery tool..");
							System.exit(0);
							break;
						}
					}
				} else
					System.out.println("Entered parameter is bad, see Package delivery tool!");
			}
		}
	}

	/**
	 * Find enumeration
	 * @see ConsoleParameterEnum by entered attribute value
	 *
	 * @param argument input in console
	 * @return enumeration
	 */
	private ConsoleParameterEnum findArgumentByValue(String argument) {
		List<ConsoleParameterEnum> enums = Arrays.asList(ConsoleParameterEnum.values());
		if (!enums.isEmpty()) {
			for (ConsoleParameterEnum param: enums) {
				if (param.getArgument().equals(argument))
					return param;
			}
		}
		return null;
	}

	/**
	 * Save manually added package.
	 * @param scanner
	 */
	private void addNewPackage(Scanner scanner) {
		System.out.print("Enter package (<weight> <postal code>): ");
		// Package validation
		PostalPackage pcg = helper.valid(scanner);
		// Package saving in memory
		PostalPackage savedPcg = packageService.save(pcg);
		// Print to console info about added package
		packageService.printPackageInfo(savedPcg, false);
	}

	/**
	 * Print help menu in console.
	 */
	private void setHelpMenu() {
		System.out.println("Package delivery tool:");
		System.out.println("-n, -new	Enter the new package ");
		System.out.println("-i, -init	Read packages from file");
		System.out.println("-f, -file	Read fees from file");
		System.out.println("-q, -quit	Exit from tool");
	}
}
