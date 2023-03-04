package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId){
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId,String partnerId){
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId){
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return orderRepository.getPartnerById(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId){
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders(){
        return orderRepository.getAllOrders();
    }

    public int getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        String[] t = time.split(":");
        int time1 = Integer.parseInt(t[0])*60 + Integer.parseInt(t[1]);
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time1,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        int time1 = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        String hh = String.valueOf(time1/60);
        String mm = String.valueOf(time1%60);
        if(hh.length() < 2){
            hh = "0"+hh;
        }
        if(mm.length() < 2){
            mm = "0"+mm;
        }

        String time = hh+":"+mm;

        //Return the time when that partnerId will deliver his last delivery order.

        return time;
    }

    public void deletePartnerById(String partnerId){
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId){
        orderRepository.deleteOrderById(orderId);
    }
}
