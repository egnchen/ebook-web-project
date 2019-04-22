package com.eyek.ebook.repository;

import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    Page<OrderItem> findByOrder(Order order, Pageable pageable);
}
