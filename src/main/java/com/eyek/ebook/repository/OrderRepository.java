package com.eyek.ebook.repository;

import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByStatusAndUser(Order.OrderStatus status, User user);
}
