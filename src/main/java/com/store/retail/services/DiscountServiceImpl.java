package com.store.retail.services;

import com.store.retail.mapper.DiscountMapper;
import com.store.retail.models.Bill;
import com.store.retail.models.ProductType;
import com.store.retail.models.User;

import java.math.BigDecimal;

public class DiscountServiceImpl implements DiscountService {

    @Override
    public BigDecimal discountCalculation(User user, Bill bill) {
        DiscountMapper helper = new DiscountMapper();

        BigDecimal totalAmount = helper.calculateTotal(bill.getProductList());
        BigDecimal phoneAmount = helper.calculateTotalPerType(bill.getProductList(), ProductType.PHONE);
        BigDecimal nonPhoneAmount = totalAmount.subtract(phoneAmount);
        BigDecimal userDiscount = helper.getUserDiscount(user);
        BigDecimal billsDiscount = helper.calculateBillsDiscount(totalAmount, new BigDecimal(200), new BigDecimal(5));
        if (nonPhoneAmount.compareTo(BigDecimal.ZERO) > 0) {
            nonPhoneAmount = helper.calculateDiscount(nonPhoneAmount, userDiscount);
        }

        return (phoneAmount.add(nonPhoneAmount).subtract(billsDiscount));
    }

}
