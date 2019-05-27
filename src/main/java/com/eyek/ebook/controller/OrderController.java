package com.eyek.ebook.controller;

import com.eyek.ebook.model.Order;
import com.eyek.ebook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    OrderService orderService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/orders")
    public Page<Order> getOrders(@Positive @RequestParam(defaultValue = "1") Integer pageNumber) {
        pageNumber -= 1;
        return orderService.getCurrentUserOrders(pageNumber);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all-orders")
    public Page<Order> getAllOrders(@RequestParam(required = false) Integer bookId,
                                    @Positive @RequestParam(defaultValue = "1") Integer pageNumber) {
        pageNumber -= 1;
        if(bookId == null)
            return orderService.getAllOrders(pageNumber);
        else
            return orderService.getOrdersByBook(bookId, pageNumber);
    }
}
