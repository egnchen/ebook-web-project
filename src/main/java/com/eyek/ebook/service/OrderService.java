package com.eyek.ebook.service;

import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.util.OutOfStockException;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Positive;

public interface OrderService {

    boolean addOrderItem(@Positive int bookId, @Positive int amount);
    boolean modifyOrderItem(@Positive int orderItemId, @Positive int amount);
    boolean deleteOrderItem(@Positive int orderItemId);

    Order getCurrentCartOrder();
    Page<OrderItem> getCurrentCartItems(int pageNumber);
    boolean deleteCurrentCartOrder();
    boolean submitCurrentCartOrder() throws OutOfStockException;
}
