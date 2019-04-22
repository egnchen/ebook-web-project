package com.eyek.ebook.service;

import com.eyek.ebook.model.Order;

public interface OrderService {
    Order getCurrentCartOrder();
    void deleteCurrentCartOrder();
    void submitCurrentCartOrder();
}
