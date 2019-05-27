package com.eyek.ebook.dao;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.OrderItemRepository;
import com.eyek.ebook.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order getCartOrder(User user) {
        return orderRepository.findByStatusAndUser(Order.OrderStatus.cart, user);
    }

    @Override
    public Order createCartOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.OrderStatus.cart);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Boolean deleteCartOrder(User user) {
        Order order = getCartOrder(user);
        if(order != null) {
            orderRepository.delete(order);
            return true;
        } else return false;
    }

    @Override
    public Page<OrderItem> getOrderItems(Order order, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderItemRepository.findByOrder(order, pageRequest);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Page<Order> getOrders(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAll(pageRequest);
    }

    @Override
    public Page<Order> getAllOrders(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAll(pageRequest);
    }

    @Override
    public Page<Order> getOrders(User user, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findOrdersByUser(user, pageRequest);
    }

    @Override
    public Page<Order> getOrdersDetailed(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findOrdersWIthItem(pageRequest);
    }

    @Override
    public Page<Order> getOrdersDetailed(User user, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findOrdersWithItemByUser(user, pageRequest);
    }

    @Override
    public Page<Order> searchBookOrders(String query, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findOrdersByBookTitleContaining(query, pageRequest);
    }

    @Override
    public Page<Order> getOrdersByBook(Book book, @PositiveOrZero int pageNumber, @Positive int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findOrdersByBook(book, pageRequest);
    }

    @Override
    public Boolean addCartItem(Order order, OrderItem orderItem) {
        List<OrderItem> items = order.getOrderItems();
        for (OrderItem item : items) {
            if (item.getBook() == orderItem.getBook()) {
                item.setAmount(item.getAmount() + orderItem.getAmount());
                orderItemRepository.save(item);
                return true;
            }
        }
        orderItem.setOrder(order);
        orderItemRepository.save(orderItem);
        return true;
    }

    @Override
    public List<Order> getOrderByUserBetween(User user, Date startTime, Date endTime) {
        return orderRepository.findOrdersWithItemByUserAndUpdateTimeBetween(user, startTime, endTime);
    }
}
