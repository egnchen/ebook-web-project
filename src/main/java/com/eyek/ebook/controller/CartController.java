package com.eyek.ebook.controller;

import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.repository.BookRepository;
import com.eyek.ebook.repository.CartRepository;
import com.eyek.ebook.repository.OrderItemRepository;
import com.eyek.ebook.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/cart")
    public Order getCart() {
        Order cart = cartRepository.getCartOrder();
        if(cart == null) {
            cart = new Order();
            cart.setStatus(Order.OrderStatus.cart);
            cartRepository.save(cart);
        }
        return cart;
    }

    @PostMapping("/cart")
    public Message addCartItem(@RequestParam int bookId, @RequestParam(defaultValue = "1") int amount) {
        if(amount < 0) return new Message("Input error", "Amount should be greater than zero.");
        Order cart = cartRepository.getCartOrder();
        for (OrderItem item : cart.getOrderItems()) {
            if(item.getBook().getId() == bookId) {
                item.setAmount(amount);
                cartRepository.save(cart);
                return new Message("OK", null);
            }
        }
        // add new one
        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(amount);
        orderItem.setBook(bookRepository.getOne(bookId));
        orderItem.setOrder(cart);
        orderItemRepository.save(orderItem);
        cartRepository.save(cart);
        return new Message("OK", null);
    }

    @PutMapping("/cart")
    public Message modifyCartItem(@RequestParam int orderItemId, @RequestParam int amount) {
        if(amount < 0) return new Message("Input error", "Amount shouldn't be less than zero.");
        OrderItem orderItem = orderItemRepository.getOne(orderItemId);
        if(orderItem.getOrder().getStatus() == Order.OrderStatus.cart) {
            if(amount == 0) orderItemRepository.delete(orderItem);
            else {
                orderItem.setAmount(amount);
                orderItemRepository.save(orderItem);
            }
            return new Message("OK", null);
        } else return new Message("Input error", "Wrong order item id.");
    }

}