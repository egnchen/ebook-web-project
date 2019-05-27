package com.eyek.ebook.service;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.model.User;
import com.eyek.ebook.util.OutOfStockException;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Positive;

public interface OrderService {

    // manipulate order items
    boolean addOrderItem(@Positive int bookId, @Positive int amount);
    boolean modifyOrderItem(@Positive int orderItemId, @Positive int amount);
    boolean deleteOrderItem(@Positive int orderItemId);

    // cart order manipulations
    Order getCurrentCartOrder();
    Page<OrderItem> getCurrentCartItems(int pageNumber);

    boolean deleteCurrentCartOrder();
    boolean submitCurrentCartOrder() throws OutOfStockException;

    // manipulate orders
    Page<Order> getCurrentUserOrders(int pageNumber);
    Page<Order> getAllOrders(int pageNumber);
    Page<Order> getOrdersByUser(User user, int pageNumber);
    Page<Order> getOrdersByBook(Book book, int pageNumber);
    Page<Order> getOrdersByBook(int bookId, int pageNumber);
}
