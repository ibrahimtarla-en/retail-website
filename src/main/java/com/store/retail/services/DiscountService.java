package com.store.retail.services;

import com.store.retail.models.Bill;
import com.store.retail.models.User;

import java.math.BigDecimal;

public interface DiscountService {
    /**
     * This method calculate discount for given user and bill
     *
     * @param user - User
     * @param bill - Bill
     * @return Double - payable amount for the user
     */
    BigDecimal discountCalculation(User user, Bill bill);
}
