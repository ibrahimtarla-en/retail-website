package com.store.retail.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private UserType type;

    private LocalDate registerDate;

    private Card card;
}
