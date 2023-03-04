package com.driver;

import org.jetbrains.annotations.NotNull;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(){

    }
    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        String[] time = deliveryTime.split(":");
        int hh = Integer.valueOf(time[0]);
        int mm = Integer.valueOf(time[1]);
        this.deliveryTime = hh*60 + mm;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}
