package com.eyek.ebook.service;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.OrderRepository;
import com.eyek.ebook.util.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @Override
    public Order getCurrentCartOrder() {
        User user = securityService.getCurrentUser();
        if(user == null)
            return null;
        Order order = orderRepository.findByStatusAndUser(Order.OrderStatus.cart, user);
        if(order == null) {
            order = new Order();
            order.setUser(user);
            order.setStatus(Order.OrderStatus.cart);
            orderRepository.save(order);
        }
        return order;
    }

    @Override
    public void deleteCurrentCartOrder() {
        User user = securityService.getCurrentUser();
        Order order = orderRepository.findByStatusAndUser(Order.OrderStatus.cart, user);
        if(order != null)
            orderRepository.delete(order);
    }

    @Override
    @Transactional
    public void submitCurrentCartOrder() throws OutOfStockException {
        User user = securityService.getCurrentUser();
        Order order = orderRepository.findByStatusAndUser(Order.OrderStatus.cart, user);
        if(order != null) {
            // check storage
            for(OrderItem orderItem: order.getOrderItems()) {
                Book book = orderItem.getBook();
                System.out.println(String.format("Checking book[%s] stock=%d amount=%d",book.getTitle(), book.getStock(), orderItem.getAmount()));
                if(orderItem.getAmount() <= book.getStock()) {
                    book.setStock(book.getStock() - orderItem.getAmount());
                } else {
                    // out of stock
                    throw new OutOfStockException(orderItem);
                }
            }
            order.setStatus(Order.OrderStatus.submitted);
            orderRepository.save(order);
        }
    }
}