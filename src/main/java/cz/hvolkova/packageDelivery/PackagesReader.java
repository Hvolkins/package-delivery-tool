package cz.hvolkova.packageDelivery;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * This component call one per 60 seconds and showing all packages, saved in memory
 *
 * @author Elena Hvolkova
 */
@Component
public class PackagesReader {
    private PostalPackageService packageService;

    public PackagesReader(final PostalPackageService packageService) {
        this.packageService = packageService;
    }

    @Scheduled(fixedRate = 60000)
    public void showPackages() {
        System.out.println("Get all packages(" + new Date() + "): ");
        List<PostalPackage> packages = packageService.getAllPackages();
        if (!packages.isEmpty()) {
            for (PostalPackage pcg: packages)
                // Printing info about package into console
                packageService.printPackageInfo(pcg, true);
        }
        System.out.println("Enter some parameter in console..");
    }
}
