package com.eyek.ebook.service;

import com.eyek.ebook.model.Order;
import com.eyek.ebook.util.OutOfStockException;

public interface OrderService {
    Order getCurrentCartOrder();
    void deleteCurrentCartOrder();
    void submitCurrentCartOrder() throws OutOfStockException;
}
