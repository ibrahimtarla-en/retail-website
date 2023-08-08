package com.store.retail.mapper;

import com.store.retail.models.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class DiscountMapper {
    private static final int YEARS_FOR_DISCOUNT = 2;

    private static final double GOLD_CARD_DISCOUNT_PERCENTAGE = 0.30;
    private static final double SILVER_CARD_DISCOUNT_PERCENTAGE = 0.20;
    private static final double AFFILIATE_DISCOUNT_PERCENTAGE = 0.10;
    private static final double CUSTOMER_DISCOUNT_PERCENTAGE = 0.05;

    public BigDecimal calculateTotal(List<Product> items) {
        return items.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalPerType(List<Product> items, ProductType type) {
        BigDecimal sum = new BigDecimal(0);

        if (type != null) {
            sum = items.stream().filter(i -> type.equals(i.getType())).map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return sum;
    }

    public BigDecimal getUserDiscount(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }

        BigDecimal discount = new BigDecimal(0);

        UserType userType = user.getType();


        if(user.getCard()!=null){
            CardType cardType = user.getCard().getType();
            if (cardType.equals(CardType.GOLD)) {
                discount = BigDecimal.valueOf(GOLD_CARD_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
            }
            if (cardType.equals(CardType.SILVER)) {
                discount = BigDecimal.valueOf(SILVER_CARD_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
            }
        }
        else  if (userType.equals(UserType.AFFILIATE)) {
            discount = BigDecimal.valueOf(AFFILIATE_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
        }
        else if (isCustomerSince(user.getRegisterDate(), YEARS_FOR_DISCOUNT)) {
            discount = BigDecimal.valueOf(CUSTOMER_DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_EVEN);
        }

        return discount;
    }


    public boolean isCustomerSince(LocalDate registeredDate, long years) {
        Period period = Period.between(registeredDate, LocalDate.now());
        return period.getYears() >= years;
    }

    public BigDecimal calculateBillsDiscount(BigDecimal totalAmount, BigDecimal amount, BigDecimal discountAmount) {
        int value = totalAmount.divide(amount).intValue();
        return discountAmount.multiply(new BigDecimal(value));
    }

    public BigDecimal calculateDiscount(BigDecimal amount, BigDecimal discount) {
        if (discount.doubleValue() > 1.0) {
            throw new IllegalArgumentException("Discount cannot be more than 100%");
        }

        BigDecimal x = amount.multiply(discount);
        return amount.subtract(x);
    }

}
