package cz.hvolkova.packageDelivery;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This service used for add/read information about package into memory.
 *
 * @author Elena Hvolkova
 */
@Service
public class PostalPackageService {
    private final Map<Long, PostalPackage> packagesInMemory;
    private List<Fee> feesInMemory;
    private final AtomicLong pcgKeySequence;

    /**
     * Initial parameters
     */
    public PostalPackageService() {
        this.feesInMemory = new ArrayList<>();
        this.packagesInMemory = new HashMap<>();
        this.pcgKeySequence = new AtomicLong(1L);
    }

    /**
     * Return all packages from memory
     * @return packagesInMemory
     */
    public List<PostalPackage> getAllPackages() {
        return new ArrayList<>(packagesInMemory.values());
    }

    /**
     * Saving the package
     * @param newPackage
     */
    public void save(final PostalPackage newPackage) {
        if (null != newPackage) {
            newPackage.setId(pcgKeySequence.getAndIncrement());

            // Control if fees are imported and recount fees od packages, saved in HashMap
            if (!feesInMemory.isEmpty())
                newPackage.setFee(count(newPackage.getWeight()));

            packagesInMemory.put(newPackage.getId(), newPackage);
        }
    }

    /**
     * Saving list of packages imported from file into HashMap
     * @param packages
     */
    public void saveFromFile(List<PostalPackage> packages) {
        if (null != packages && !packages.isEmpty()) {
            for (PostalPackage pcg: packages)
                save(pcg);
        }
    }

    /**
     * Recounting fees in packages after import fees from file.
     * @param fees
     * @return
     */
    public List<PostalPackage> recountFeeForPackages(List<Fee> fees) {
        if (null != fees) {
            feesInMemory = fees;
            List<PostalPackage> list = getAllPackages();
            if (!list.isEmpty()) {
                for (PostalPackage pcg: list) {
                    Double fee = count(pcg.getWeight());
                    pcg.setFee(fee);
                }
            }
            return list;
        }
        return null;
    }

    /**
     * Print information about packages in console
     * @param postPackage
     * @param allPackages
     */
    public void printPackageInfo(PostalPackage postPackage, boolean allPackages) {
        String info = allPackages ? "" : "Added package: ";

        if (feesInMemory.isEmpty())
            System.out.format(info + "%s %.3f", postPackage.getPostCode(), postPackage.getWeight()).println();
        else
            System.out.format(info + "%s %.3f %.2f", postPackage.getPostCode(), postPackage.getWeight(), postPackage.getFee()).println();
    }

    /**
     * Count fee over weight of package
     * @param weight
     * @return
     */
    private Double count(Double weight) {
        if (null != weight && !feesInMemory.isEmpty()) {
            for (int i=0; i<feesInMemory.size(); i++) {
                if (weight >= feesInMemory.get(i).getWeight())
                    return feesInMemory.get(i).getFee();
            }
        }
        return null;
    }
}
