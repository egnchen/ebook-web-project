package com.eyek.ebook.dao;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.model.User;
import org.springframework.data.domain.Page;

public interface OrderDao {

    Order getCartOrder(User user);
    Order createCartOrder(User user);
    Boolean deleteCartOrder(User user);

    Page<OrderItem> getOrderItems(Order order, int pageNumber, int pageSize);

    Order saveOrder(Order order);
    void deleteOrder(Order order);

    Page<Order> getOrders(User user, Integer pageNumber, Integer pageSize);
    Page<Order> getOrdersDetailed(User user, Integer pageNumber, Integer pageSize);
    Page<Order> searchBookOrders(String query, Integer pageNumber, Integer pageSize);
    Page<Order> getBookOrders(Book book, Integer pageNumber, Integer pageSize);

    Boolean addCartItem(Order order, OrderItem orderItem);
}
