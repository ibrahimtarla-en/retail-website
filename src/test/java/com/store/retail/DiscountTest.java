package com.store.retail;

import com.store.retail.mapper.DiscountMapper;
import com.store.retail.models.*;
import com.store.retail.services.DiscountService;
import com.store.retail.services.DiscountServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DiscountTest {


    @Test(expected = NullPointerException.class)
    public void testCalculateTotal_error() {
        DiscountMapper mapper = new DiscountMapper();
        mapper.getUserDiscount(null);
    }

    @Test
    public void testCalculateDiscount_10pct() {
        DiscountMapper mapper = new DiscountMapper();
        BigDecimal total = mapper.calculateDiscount(new BigDecimal(1000), new BigDecimal(0.1));
        assertEquals(900.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_50pct() {
        DiscountMapper mapper = new DiscountMapper();
        BigDecimal total = mapper.calculateDiscount(new BigDecimal(1000), new BigDecimal(0.5));
        assertEquals(500.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_0pct() {
        DiscountMapper mapper = new DiscountMapper();
        BigDecimal total = mapper.calculateDiscount(new BigDecimal(1000), new BigDecimal(0.0));
        assertEquals(1000.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_100pct() {
        DiscountMapper mapper = new DiscountMapper();
        BigDecimal total = mapper.calculateDiscount(new BigDecimal(1000), new BigDecimal(1.0));
        assertEquals(0.0, total.doubleValue(), 0);
    }


    @Test
    public void testCalculateTotal_PhoneOnly() {
        List<Product> items = new ArrayList<Product>();
        items.add(new Product(ProductType.PHONE, new BigDecimal(100.0)));
        items.add(new Product(ProductType.PHONE, new BigDecimal(100.0)));
        items.add(new Product(ProductType.PHONE, new BigDecimal(100.0)));

        DiscountMapper mapper = new DiscountMapper();
        BigDecimal total = mapper.calculateTotalPerType(items, ProductType.PHONE);
        assertEquals(300.00, total.doubleValue(), 0);
        System.out.println(total);
    }

    @Test
    public void testCalculateTotalNonPhoneOnly() {
        List<Product> items = new ArrayList<Product>();
        items.add(new Product(ProductType.CLOTHES, new BigDecimal(100.0)));
        items.add(new Product(ProductType.OTHER, new BigDecimal(100.0)));


        DiscountMapper mapper = new DiscountMapper();
        BigDecimal total = mapper.calculateTotal(items);
        assertEquals(200.00, total.doubleValue(), 0);
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserSpecificDiscount_customer_null_user() {
        DiscountMapper mapper = new DiscountMapper();
        mapper.getUserDiscount(null);
    }

    @Test
    public void testCalculateBillsDiscount() {
        DiscountMapper mapper = new DiscountMapper();
        BigDecimal amount = mapper.calculateBillsDiscount(new BigDecimal(1000), new BigDecimal(100), new BigDecimal(5));
        assertEquals(50, amount.doubleValue(), 0);
    }


    /**
     * Calculate discount  a full bill of a Customer who has a gold card
     */
    @Test
    public void testDiscountBill() {

        DiscountService discountService = new DiscountServiceImpl();

        List<Product> items = new ArrayList<Product>();
        items.add(new Product(ProductType.PHONE, new BigDecimal(50.0)));
        items.add(new Product(ProductType.OTHER, new BigDecimal(200.0)));
        items.add(new Product(ProductType.CLOTHES, new BigDecimal(100.0)));

        Bill bill = new Bill();
        bill.setProductList(items);

        CardType cardType = CardType.GOLD;
        Card card = new Card(cardType);

        LocalDate joinDate = LocalDate.of(2016, 2, 23);
        User user = new User(UserType.CUSTOMER, joinDate, card);
        BigDecimal finalAmount = discountService.discountCalculation(user, bill);

        assertEquals(255, finalAmount.doubleValue(), 0);


    }


    /**
     * User Has a Gold Card
     */
    @Test
    public void testGetUserSpecificDiscountUserHasGoldCard() {

        CardType cardType = CardType.GOLD;
        Card card = new Card(cardType);

        LocalDate joinDate = LocalDate.of(2016, 2, 23);
        User user = new User(UserType.CUSTOMER, joinDate, card);
        DiscountMapper mapper = new DiscountMapper();
        BigDecimal discount = mapper.getUserDiscount(user);
        assertEquals(0.3, discount.doubleValue(), 0);
    }

    /**
     * User Has a Silver Card
     */
    @Test
    public void testGetUserSpecificDiscountUserHasSilverCard() {

        CardType cardType = CardType.SILVER;
        Card card = new Card(cardType);

        LocalDate joinDate = LocalDate.of(2016, 2, 23);
        User user = new User(UserType.CUSTOMER, joinDate, card);
        DiscountMapper mapper = new DiscountMapper();
        BigDecimal discount = mapper.getUserDiscount(user);
        assertEquals(0.2, discount.doubleValue(), 0);
    }


    /**
     * User Has no Card and User has been a customer for over 2 years,
     */
    @Test
    public void testGetOldUserDiscountWithoutCard() {


        LocalDate joinDate = LocalDate.of(2016, 2, 23);
        User user = new User(UserType.CUSTOMER, joinDate, null);
        DiscountMapper mapper = new DiscountMapper();
        BigDecimal discount = mapper.getUserDiscount(user);
        assertEquals(0.05, discount.doubleValue(), 0);
    }

    /**
     * User Has no Card and User has not  been a customer for over 2 years,
     */
    @Test
    public void testGetNewUserDiscountWithoutCard() {

        User user = new User(UserType.CUSTOMER, LocalDate.now(), null);
        DiscountMapper mapper = new DiscountMapper();
        BigDecimal discount = mapper.getUserDiscount(user);
        assertEquals(0, discount.doubleValue(), 0);
    }

    /**
     * User Has no Card and  user is an affiliate of the store
     */
    @Test
    public void testGetAffiliateUserDiscountWithoutCard() {

        User user = new User(UserType.AFFILIATE, LocalDate.now(), null);
        DiscountMapper mapper = new DiscountMapper();
        BigDecimal discount = mapper.getUserDiscount(user);
        assertEquals(0.1, discount.doubleValue(), 0);
    }
}
