package com.driver;
import java.util.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public class OrderRepository {

    Map<String,Order> orderDB = new HashMap<>();
    Map<String,DeliveryPartner> deliveryPartnerDB = new HashMap<>();
    Map<String,String> orderAssignedDB = new HashMap<>();
    Map<String,List<String>> deliveryListDB = new HashMap<>();

    public void addOrder(Order order){
        orderDB.put(order.getId(),order);
    }

    public void addPartner(String partnerId){
        deliveryPartnerDB.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId,String partnerId){
        if(orderDB.containsKey(orderId) && deliveryPartnerDB.containsKey(partnerId)){
            orderAssignedDB.put(orderId,partnerId);

            List<String> ordersList = new ArrayList<>();
            if(deliveryListDB.containsKey(partnerId)){
                ordersList = deliveryListDB.get(partnerId);
            }
            ordersList.add(orderId);
            deliveryListDB.put(partnerId,ordersList);

            deliveryPartnerDB.get(partnerId).setNumberOfOrders(ordersList.size());
        }

    }

    public Order getOrderById(String orderId){
        return orderDB.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return deliveryPartnerDB.get(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId){
        return deliveryListDB.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> ordersList = new ArrayList<>();
        if(deliveryListDB.containsKey(partnerId)){
            ordersList = deliveryListDB.get(partnerId);
        }
        return ordersList;
    }

    public List<String> getAllOrders(){
        List<String> orders = new ArrayList<>();
        for(String order : orderDB.keySet()){
            orders.add(order);
        }
        return orders;
    }

    public int getCountOfUnassignedOrders(){
        return orderDB.size() - orderAssignedDB.size();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId){
        int count = 0;
        List<String> orders = deliveryListDB.get(partnerId);

        for(String order : orders){
            int deliveryTime = orderDB.get(order).getDeliveryTime();
            if(deliveryTime > time){
                count++;
            }
        }
        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId){
        int time = 0;
        List<String> orders = deliveryListDB.get(partnerId);
        for(String order : orders){
            int deliveryTime = orderDB.get(order).getDeliveryTime();
            time = Math.max(time,deliveryTime);
        }
        return time;
    }

    public void deletePartnerById(String partnerId){
        deliveryPartnerDB.remove(partnerId);

        List<String> orders = deliveryListDB.get(partnerId);

        for(String order : orders){
            orderAssignedDB.remove(order);
        }

        deliveryListDB.remove(partnerId);
    }

    public void deleteOrderById(String orderId){
        orderDB.remove(orderId);

        String partnerID = orderAssignedDB.get(orderId);
        orderAssignedDB.remove(orderId);

        deliveryListDB.get(partnerID).remove(orderId);

        deliveryPartnerDB.get(partnerID).setNumberOfOrders(deliveryListDB.get(partnerID).size());

    }
}
