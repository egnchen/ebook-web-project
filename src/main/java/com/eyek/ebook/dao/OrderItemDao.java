package com.eyek.ebook.dao;

import com.eyek.ebook.model.OrderItem;

public interface OrderItemDao {
    OrderItem getOrderItem(int id);
    OrderItem saveOrderItem(OrderItem orderItem);
    boolean deleteOrderItem(int id);
}
