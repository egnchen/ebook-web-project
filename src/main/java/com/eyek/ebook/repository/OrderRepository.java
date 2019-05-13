package com.eyek.ebook.repository;

import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByStatusAndUser(Order.OrderStatus status, User user);
    List<Order> findOrdersByUser(User user);
    List<Order> findOrdersByUser(User user, PageRequest pageRequest);

    @Query(value = "FROM Order o INNER JOIN FETCH o.orderItems WHERE o.user = ?1")
    List<Order> findAllWithItemByUser(User user);

    @Query(value = "FROM Order o INNER JOIN FETCH o.orderItems WHERE o.user = ?1",
    countQuery = "SELECT COUNT(o) FROM Order o WHERE o.user = ?1")
    Page<Order> findOrdersWithItemByUser(User user, PageRequest pageRequest);
}
