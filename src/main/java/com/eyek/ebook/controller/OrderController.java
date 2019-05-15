package com.eyek.ebook.controller;

import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    OrderRepository orderRepository;

    @PreAuthorize("hasRole('ROLE_USER,ROLE_ADMIN')")
    @GetMapping("/orders")
    public Page<Order> getOrders(@Positive @RequestParam(defaultValue = "1") Integer pageNumber) {
        User user = authenticationFacade.getCurrentUser();
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);
        return orderRepository.findOrdersByUser(user, pageRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all-orders")
    public Page<Order> getAllOrders(@RequestParam(required = false) String keyword,
                                    @Positive @RequestParam(defaultValue = "1") Integer pageNumber) {
        // mandatory pagination
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);
        if(keyword != null)
            return orderRepository.findOrdersByBookTitleContaining(keyword, pageRequest);
        else
            return orderRepository.findAll(pageRequest);
    }
}
