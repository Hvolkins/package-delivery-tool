package cz.hvolkova.packageDelivery;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Fee model
 *
 * @author Elena Hvolkova
 */
@Data
@NoArgsConstructor
public class Fee {
    private Double weight;
    private Double fee;

    public Fee(Double weight, Double fee) {
        this.weight = weight;
        this.fee = fee;
    }
}
