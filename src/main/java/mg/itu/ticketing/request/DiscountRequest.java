package mg.itu.ticketing.request;

import lombok.Data;
import mg.itu.ticketing.entity.Discount;
import mg.matsd.javaframework.validation.constraints.basic.Required;
import mg.matsd.javaframework.validation.constraints.number.Positive;

import java.math.BigDecimal;

@Data
public class DiscountRequest {
    @Required
    @Positive
    private BigDecimal percentage;

    @Required
    @Positive
    private Integer seatCount;

    @Required
    private Integer seatPricingId;

    public static DiscountRequest fromDiscount(final Discount discount) {
        DiscountRequest discountRequest = new DiscountRequest();
        discountRequest.setPercentage(discount.getPercentage());
        discountRequest.setSeatCount(discount.getSeatCount());
        discountRequest.setSeatPricingId(discount.getSeatPricing().getId());

        return discountRequest;
    }
}
