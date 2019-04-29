package com.eyek.ebook.controller;

import com.eyek.ebook.controller.dto.OrderItemDto;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.BookRepository;
import com.eyek.ebook.repository.OrderItemRepository;
import com.eyek.ebook.repository.OrderRepository;
import com.eyek.ebook.repository.UserRepository;
import com.eyek.ebook.service.OrderService;
import com.eyek.ebook.service.SecurityService;
import com.eyek.ebook.util.Message;
import com.eyek.ebook.util.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/api")
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/cart")
    public Page<OrderItem> getCart(@RequestParam(defaultValue = "0") int pageId) {
        Pageable pageable = PageRequest.of(pageId, 5);
        Order order = orderService.getCurrentCartOrder();
        return orderItemRepository.findByOrder(order, pageable);
    }

    // add order item to cart
    // add new item or add amount to item that already exists
    @PostMapping("/cart")
    public Message addCartItem(@RequestBody OrderItemDto orderItemDto, HttpServletResponse response) {
        try {
            Order cart = orderService.getCurrentCartOrder();
            for (OrderItem item : cart.getOrderItems()) {
                if (item.getBook().getId() == orderItemDto.getBookId()) {
                    item.setAmount(item.getAmount() + orderItemDto.getAmount());
                    if (item.getAmount() > item.getBook().getStock())
                        throw new OutOfStockException(item);
                    orderRepository.save(cart);
                    return new Message("OK", null);
                }
            }
            // add new one
            OrderItem orderItem = new OrderItem();
            orderItem.setAmount(orderItemDto.getAmount());
            orderItem.setBook(bookRepository.getOne(orderItemDto.getBookId()));
            orderItem.setOrder(cart);
            orderItemRepository.save(orderItem);
            if (orderItemDto.getAmount() > bookRepository.getOne(orderItemDto.getBookId()).getStock()) {
                // out of stock
                throw new OutOfStockException(orderItem);
            }
            orderRepository.save(cart);
            return new Message("OK", null);
        } catch (OutOfStockException e) {
            response.setStatus(400);
            return new Message("error", String.format("book %s out of stock.", e.getOrderItem().getBook().getTitle()));
        }
    }

    // TODO security exploitable
    @PutMapping("/cart")
    public Message modifyCartItem(@RequestBody OrderItem orderItem) {
        OrderItem orderItemInRepo = orderItemRepository.getOne(orderItem.getId());
        if(orderItemInRepo != null) {
            if(orderItem.getAmount() == 0) orderItemRepository.delete(orderItemInRepo);
            else {
                orderItemInRepo.setAmount(orderItem.getAmount());
                orderItemRepository.save(orderItemInRepo);
            }
            return new Message("OK", null);
        } else
            return new Message("Input error", "Wrong order item id.");
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
    public Message submitOrder(HttpServletResponse response) {
        try {
            orderService.submitCurrentCartOrder();
        } catch(OutOfStockException e) {
            response.setStatus(400);
            return new Message("Error", String.format("order for book %s out of stock.", e.getOrderItem().getBook().getTitle()));
        }
        return new Message("OK", null);
    }

    @GetMapping("/orders")
    public List<Order> getOrders(@RequestParam(required = false) Integer userId, @Positive@RequestParam(required = false) Integer pageNumber) {
        User user;
        if(userId == null)
            user = securityService.getCurrentUser();
        else {
            user = userRepository.findById(userId).get();
            if (user == null)
                return null;
        }
        if(pageNumber != null) {
            PageRequest pageRequest = PageRequest.of(pageNumber, 10);
            return orderRepository.findOrdersByUser(user, pageRequest);
        } else
            return orderRepository.findOrdersByUser(user);
    }

}