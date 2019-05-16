package com.eyek.ebook.dao;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.User;
import org.springframework.data.domain.Page;

public interface OrderDao {

    Page<Order> getOrders(User user, Integer pageNumber, Integer pageSize);
    Page<Order> getOrdersDetailed(User user, Integer pageNumber, Integer pageSize);
    Page<Order> searchBookOrders(String query, Integer pageNumber, Integer pageSize);
    Page<Order> getBookOrders(Book book, Integer pageNumber, Integer pageSize);
}
