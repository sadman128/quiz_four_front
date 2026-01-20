package com.sajid.quiz_four_front.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String customer;
    private String product;
    private int quantity;
    private double price;
    private String status;
    private Date date;
}