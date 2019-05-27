package com.eyek.ebook.dao;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.model.User;
import org.springframework.data.domain.Page;

public interface OrderDao {

    Page<Order> getAllOrders(int pageNumber, int pageSize);
    Order getCartOrder(User user);
    Order createCartOrder(User user);
    Boolean deleteCartOrder(User user);

    Page<OrderItem> getOrderItems(Order order, int pageNumber, int pageSize);

    Order saveOrder(Order order);
    void deleteOrder(Order order);

    Page<Order> getOrders(int pageNumber, int pageSize);
    Page<Order> getOrders(User user, int pageNumber, int pageSize);
    Page<Order> getOrdersDetailed(int pageNumber, int pageSize);
    Page<Order> getOrdersDetailed(User user, int pageNumber, int pageSize);
    Page<Order> searchBookOrders(String query, int pageNumber, int pageSize);
    Page<Order> getOrdersByBook(Book book, int pageNumber, int pageSize);

    Boolean addCartItem(Order order, OrderItem orderItem);
}
