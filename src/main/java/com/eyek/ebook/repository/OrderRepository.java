package com.eyek.ebook.repository;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "FROM Order o INNER JOIN FETCH o.orderItems",
    countQuery = "SELECT COUNT(o) FROM Order o")
    Page<Order> findOrdersWIthItem(Pageable pageable);

    Order findByStatusAndUser(Order.OrderStatus status, User user);

    Page<Order> findOrdersByUser(User user, Pageable pageable);

    @Query(value = "SELECT DISTINCT(o) FROM Order o INNER JOIN FETCH o.orderItems WHERE o.user = ?1",
    countQuery = "SELECT COUNT(DISTINCT o) FROM Order o WHERE o.user = ?1")
    Page<Order> findOrdersWithItemByUser(User user, Pageable pageable);

    @Query(value = "SELECT DISTINCT(oi.order) FROM Book b INNER JOIN FETCH OrderItem oi WHERE b.title LIKE %?1%",
    countQuery = "SELECT COUNT(DISTINCT oi.order) FROM Book b INNER JOIN FETCH OrderItem oi WHERE b.title LIKE %?1%")
    Page<Order> findOrdersByBookTitleContaining(String query, Pageable pageable);

    @Query(value = "SELECT DISTINCT oi.order FROM OrderItem oi WHERE oi.book = ?1",
    countQuery = "SELECT COUNT(DISTINCT oi.order) FROM OrderItem oi WHERE oi.book = ?1")
    Page<Order> findOrdersByBook(Book book, Pageable pageable);

    @Query(value = "SELECT DISTINCT o FROM Order o INNER JOIN FETCH o.orderItems oi INNER JOIN FETCH oi.book WHERE o.user = ?1 AND o.updateTime > ?2 AND o.updateTime < ?3 ")
    List<Order> findOrdersWithItemByUserAndUpdateTimeBetween(User user, Date startTime, Date endTime);

    List<Order> findOrdersByUpdateTimeBetween(Date startTime, Date endTime);
}
