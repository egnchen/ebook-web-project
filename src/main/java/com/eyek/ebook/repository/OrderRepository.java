package com.eyek.ebook.repository;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByStatusAndUser(Order.OrderStatus status, User user);

    Page<Order> findOrdersByUser(User user, PageRequest pageRequest);

    @Query(value = "FROM Order o INNER JOIN FETCH o.orderItems WHERE o.user = ?1",
    countQuery = "SELECT COUNT(o) FROM Order o WHERE o.user = ?1")
    Page<Order> findOrdersWithItemByUser(User user, PageRequest pageRequest);

    @Query(value = "SELECT DISTINCT(oi.order) FROM Book b INNER JOIN FETCH OrderItem oi WHERE b.title LIKE %?1%",
    countQuery = "SELECT COUNT(DISTINCT oi.order) FROM Book b INNER JOIN FETCH OrderItem oi WHERE b.title LIKE %?1%")
    Page<Order> findOrdersByBookTitleContaining(String query, PageRequest pageRequest);

    @Query(value = "SELECT DISTINCT oi.order FROM Book b INNER JOIN FETCH OrderItem oi WHERE b = ?1",
    countQuery = "SELECT COUNT(DISTINCT oi.order) FROM Book b INNER JOIN FETCH OrderItem oi WHERE b = ?1")
    Page<Order> findOrdersByBook(Book book, PageRequest pageRequest);
}
