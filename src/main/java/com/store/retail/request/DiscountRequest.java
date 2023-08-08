package com.store.retail.request;

import com.store.retail.models.Bill;
import com.store.retail.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountRequest {

    private User user;

    private Bill bill;

}
