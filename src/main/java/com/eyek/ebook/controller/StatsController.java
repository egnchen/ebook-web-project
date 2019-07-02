package com.eyek.ebook.controller;

import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.User;
import com.eyek.ebook.service.BookService;
import com.eyek.ebook.service.StatsService;
import com.eyek.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/stats")
@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;

    @GetMapping("/orders")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<List<Order>> getOrdersBetween(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
            @RequestParam(required = false) String username) {
        if(startTime == null) // from the start of the day
            startTime = new Date(0);
        if(endTime == null) // till today
            endTime = new Date();

        if(startTime.getTime() >= endTime.getTime())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User targetUser;
        if(username != null) {
            targetUser = userService.findByUsername(username);
            if (targetUser == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else
            targetUser = authenticationFacade.getCurrentUser();
        return new ResponseEntity<>(
                statsService.getPurchaseByUserBetween(startTime, endTime, targetUser),
                HttpStatus.OK);
    }

    @GetMapping("/orders/by-book")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<Order>> getOrdersForBook(@RequestParam Integer bookId) {
        Book book = bookService.getBook(bookId);
        if(book == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(statsService.getAllPurchasesForBook(book), HttpStatus.OK);
    }

    @GetMapping("/books")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<Map<String, Float>> getBookPurchasesBetween(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
            @RequestParam(required = false) String username) {

        if(startTime == null) // from the start of the day
            startTime = new Date(0);
        if(endTime == null) // till today
            endTime = new Date();
        if(startTime.getTime() >= endTime.getTime())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User targetUser = null;
        if(authenticationFacade.getCurrentUser().getRole() == User.Role.ROLE_USER) {
            // ordinary users can only view their own stats
            targetUser = authenticationFacade.getCurrentUser();
        } else if(username != null) {
            targetUser = userService.findByUsername(username);
            if (targetUser == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Map<Book, Integer> bookPurchases;
        if(targetUser == null)
            bookPurchases = statsService.getPurchaseStats(statsService.getAllPurchasesBetween(startTime, endTime));
        else
            bookPurchases = statsService.getPurchaseStats(statsService.getPurchaseByUserBetween(startTime, endTime, targetUser));

        Map<String, Float> ret = new HashMap<>();
        Float totalPurchase = 0f;
        for(Map.Entry<Book, Integer> entry: bookPurchases.entrySet()) {
            String title = String.format("%s(RMB%.2f)", entry.getKey().getTitle(), entry.getKey().getPrice());
            ret.put(title, Float.valueOf(entry.getValue()));
            totalPurchase += entry.getKey().getPrice() * entry.getValue();
        }
        ret.put("_totalPurchase", totalPurchase);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

}
