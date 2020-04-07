package cz.hvolkova.packageDelivery;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Postal package model
 *
 * @author Elena Hvolkova
 */
@Data
@NoArgsConstructor
public class PostalPackage {
    private Long id;
    private Double weight;
    private Double fee;
    private Integer postalCode;

    public PostalPackage(Double weight, Integer postalCode) {
        this.weight = weight;
        this.postalCode = postalCode;
    }
}
