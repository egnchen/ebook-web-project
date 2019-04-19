package com.eyek.ebook.repository;

import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(Order order, PageRequest pageRequest);
}
