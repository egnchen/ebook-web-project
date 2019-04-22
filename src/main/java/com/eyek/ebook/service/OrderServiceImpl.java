package com.eyek.ebook.service;

import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Override
    public Order getCurrentCartOrder() {
        User user = authenticationFacade.getCurrentUser();
        if(user != null) {
            Order order = orderRepository.findByStatusAndUser(
                    Order.OrderStatus.cart, authenticationFacade.getCurrentUser());
            if(order == null) {
                order = new Order();
                order.setUser(user);
                order.setStatus(Order.OrderStatus.cart);
                orderRepository.save(order);
            }
            return order;
        }
        else
            return null;
    }

    @Override
    public void deleteCurrentCartOrder() {
        User user = authenticationFacade.getCurrentUser();
        Order order = orderRepository.findByStatusAndUser(Order.OrderStatus.cart, user);
        if(order != null)
            orderRepository.delete(order);
    }

    @Override
    public void submitCurrentCartOrder() {
        User user = authenticationFacade.getCurrentUser();
        Order order = orderRepository.findByStatusAndUser(Order.OrderStatus.cart, user);
        if(order != null) {
            order.setStatus(Order.OrderStatus.submitted);
            orderRepository.save(order);
        }
    }
}
