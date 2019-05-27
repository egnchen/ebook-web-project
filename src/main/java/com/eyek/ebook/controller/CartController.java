package com.eyek.ebook.controller;

import com.eyek.ebook.controller.dto.OrderItemDto;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.service.OrderService;
import com.eyek.ebook.util.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/api")
@RestController
public class CartController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/cart")
    public Page<OrderItem> getCart(@RequestParam(defaultValue = "0") int pageId) {
        return orderService.getCurrentCartItems(pageId);
    }

    // add order item to cart
    // add new item or add amount to item that already exists
    @PostMapping("/cart")
    public ResponseEntity<String> addCartItem(@RequestBody OrderItemDto orderItemDto, HttpServletResponse response) {
        if(orderService.addOrderItem(orderItemDto.getBookId(), orderItemDto.getAmount()))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/cart")
    public ResponseEntity<String> modifyCartItem(@RequestBody OrderItem orderItem) {
        if(orderService.modifyOrderItem(orderItem.getId(), orderItem.getAmount())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> removeCartItem(@RequestParam int orderItemId) {
        if(orderService.deleteOrderItem(orderItemId))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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