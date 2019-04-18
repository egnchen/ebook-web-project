package com.eyek.ebook.repository;

import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.status = cart")
    public Order getCartOrder();

}
