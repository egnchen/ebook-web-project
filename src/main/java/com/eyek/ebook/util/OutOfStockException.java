package com.eyek.ebook.util;

import com.eyek.ebook.model.OrderItem;

public class OutOfStockException extends Exception {
    private OrderItem orderItem;

    public OutOfStockException(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }
}
