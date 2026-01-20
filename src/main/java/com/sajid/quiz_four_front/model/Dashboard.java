package com.sajid.quiz_four_front.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {
    public int totalOrder;
    public double revenue;
    public int pendingOrder;
    public List<Order> oderList;

    public Dashboard(int i, double i1, int i2) {
        this.totalOrder = i;
        this.revenue = i1;
        this.pendingOrder = i2;
    }
}
