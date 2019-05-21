package com.eyek.ebook.dao;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Page<Order> getOrders(User user, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findOrdersByUser(user, pageRequest);
    }

    @Override
    public Page<Order> getOrdersDetailed(User user, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findOrdersWithItemByUser(user, pageRequest);
    }

    @Override
    public Page<Order> searchBookOrders(String query, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findOrdersByBookTitleContaining(query, pageRequest);
    }

    @Override
    public Page<Order> getBookOrders(Book book, @PositiveOrZero Integer pageNumber, @Positive Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findOrdersByBook(book, pageRequest);
    }
}
