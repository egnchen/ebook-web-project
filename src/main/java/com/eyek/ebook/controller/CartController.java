package com.eyek.ebook.controller;

import com.eyek.ebook.facade.LoggerFacade;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.repository.BookRepository;
import com.eyek.ebook.repository.OrderItemRepository;
import com.eyek.ebook.repository.OrderRepository;
import com.eyek.ebook.service.CartService;
import com.eyek.ebook.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequestMapping("/api")
@RestController
public class CartController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/cart")
    public List<OrderItem> getCart(@RequestParam(defaultValue = "0") int pageId) {
        PageRequest pageRequest = PageRequest.of(pageId, 5);
        return orderItemRepository.findByOrder(cartService.getCurrentCartOrder(), pageRequest);
    }

    @PostMapping("/cart")
    public Message addCartItem(@RequestParam int bookId,
                               @RequestParam(defaultValue = "1") @PositiveOrZero int amount) {
        Order cart = cartService.getCurrentCartOrder();
        for (OrderItem item : cart.getOrderItems()) {
            if(item.getBook().getId() == bookId) {
                item.setAmount(amount);
                orderRepository.save(cart);
                return new Message("OK", null);
            }
        }
        // add new one
        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(amount);
        orderItem.setBook(bookRepository.getOne(bookId));
        orderItem.setOrder(cart);
        orderItemRepository.save(orderItem);
        LoggerFacade.getLogger().debug("orderItem saved.");
        orderRepository.save(cart);
        return new Message("OK", null);
    }

    @PutMapping("/cart")
    public Message modifyCartItem(@RequestParam int orderItemId, @RequestParam @PositiveOrZero int amount) {
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

    @DeleteMapping("/cart")
    public Message removeCartItem(@RequestParam int orderItemId) {
        OrderItem orderItem = orderItemRepository.getOne(orderItemId);
        if(orderItem.getOrder().getStatus() == Order.OrderStatus.cart) {
            orderItemRepository.delete(orderItem);
            return new Message("OK", null);
        } else return new Message("Input error", "Wrong order item id.");
    }

    @PostMapping("/cart/submit")
    public Message submitOrder() {
        cartService.deleteCurrentCartOrder();
        return new Message("OK", null);
    }
}