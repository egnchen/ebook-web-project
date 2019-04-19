package com.eyek.ebook.service;

import com.eyek.ebook.model.Order;

public interface CartService {
    Order getCurrentCartOrder();
    void deleteCurrentCartOrder();
}
