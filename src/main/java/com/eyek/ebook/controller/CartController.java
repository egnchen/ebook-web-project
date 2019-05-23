package com.eyek.ebook.controller;

import com.eyek.ebook.controller.dto.OrderItemDto;
import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.BookRepository;
import com.eyek.ebook.repository.OrderItemRepository;
import com.eyek.ebook.repository.OrderRepository;
import com.eyek.ebook.repository.UserRepository;
import com.eyek.ebook.service.OrderService;
import com.eyek.ebook.util.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.util.List;

@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/api")
@RestController
public class CartController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/cart")
    public Page<OrderItem> getCart(@RequestParam(defaultValue = "0") int pageId) {
        Pageable pageable = PageRequest.of(pageId, 5);
        Order order = orderService.getCurrentCartOrder();
        return orderItemRepository.findByOrder(order, pageable);
    }

    // add order item to cart
    // add new item or add amount to item that already exists
    @PostMapping("/cart")
    public ResponseEntity<String> addCartItem(@RequestBody OrderItemDto orderItemDto, HttpServletResponse response) {
        try {
            Order cart = orderService.getCurrentCartOrder();
            for (OrderItem item : cart.getOrderItems()) {
                if (item.getBook().getId() == orderItemDto.getBookId()) {
                    item.setAmount(item.getAmount() + orderItemDto.getAmount());
                    if (item.getAmount() > item.getBook().getStock())
                        throw new OutOfStockException(item);
                    orderRepository.save(cart);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
            // add new one
            OrderItem orderItem = new OrderItem();
            orderItem.setAmount(orderItemDto.getAmount());
            orderItem.setBook(bookRepository.getOne(orderItemDto.getBookId()));
            orderItem.setOrder(cart);
            if (orderItemDto.getAmount() > bookRepository.getOne(orderItemDto.getBookId()).getStock()) {
                // out of stock
                throw new OutOfStockException(orderItem);
            }
            orderItemRepository.save(orderItem);
            orderRepository.save(cart);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OutOfStockException e) {
            response.setStatus(400);
            return new ResponseEntity<>(
                    String.format("book %s out of stock.", e.getOrderItem().getBook().getTitle()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cart")
    public ResponseEntity<String> modifyCartItem(@RequestBody OrderItem orderItem, HttpServletResponse response) {
        OrderItem orderItemInRepo = orderItemRepository.getOne(orderItem.getId());
        if(orderItemInRepo != null) {
            if(orderItemInRepo.getOrder() != orderService.getCurrentCartOrder()) {
                // illegal option - modifying other user's order item
                response.setStatus(403);
                return new ResponseEntity<>("Wrong order id", HttpStatus.FORBIDDEN);
            }
            if(orderItem.getAmount() == 0) orderItemRepository.delete(orderItemInRepo);
            else {
                orderItemInRepo.setAmount(orderItem.getAmount());
                orderItemRepository.save(orderItemInRepo);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            response.setStatus(400);
            return new ResponseEntity<>("Wrong order item id.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> removeCartItem(@RequestParam int orderItemId, HttpServletResponse response) {
        OrderItem orderItem = orderItemRepository.getOne(orderItemId);
        if(orderItem != null) {
            if(orderItem.getOrder() != orderService.getCurrentCartOrder()) {
                response.setStatus(403);
                return new ResponseEntity<>("Wrong order id.", HttpStatus.FORBIDDEN);
            }
            if(orderItem.getOrder().getStatus() == Order.OrderStatus.cart) {
                orderItemRepository.delete(orderItem);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                response.setStatus(403);
                return new ResponseEntity<>("Wrong order item id, item not in cart.", HttpStatus.FORBIDDEN);
            }
        } else {
            response.setStatus(400);
            return new ResponseEntity<>("Wrong order item id.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cart/submit")
    public ResponseEntity<String> submitOrder(HttpServletResponse response) {
        try {
            orderService.submitCurrentCartOrder();
        } catch(OutOfStockException e) {
            response.setStatus(400);
            return new ResponseEntity<>(
                    String.format("order for book %s out of stock.", e.getOrderItem().getBook().getTitle()),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}