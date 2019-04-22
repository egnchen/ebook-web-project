package com.eyek.ebook.repository;

import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByStatusAndUser(Order.OrderStatus status, User user);
    List<Order> findOrdersByUser(User user);
    List<Order> findOrdersByUser(User user, PageRequest pageRequest);
}
