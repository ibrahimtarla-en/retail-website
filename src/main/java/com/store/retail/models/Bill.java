package com.store.retail.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Bill {

    private List<Product> productList;

}
