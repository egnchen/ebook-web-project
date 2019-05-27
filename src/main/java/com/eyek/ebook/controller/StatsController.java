package com.eyek.ebook.controller;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrdersBetween(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {
        if(startTime == null) // from the start of the day
            startTime = new Date(0);
        if(endTime == null) // till today
            endTime = new Date();

        if(startTime.getTime() >= endTime.getTime())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(statsService.getPurchaseByUserBetween(startTime, endTime),
                HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<Map<String, Integer>> getBookPurchasesBetween(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {
        if(startTime == null) // from the start of the day
            startTime = new Date(0);
        if(endTime == null) // till today
            endTime = new Date();

        if(startTime.getTime() >= endTime.getTime())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Map<Book, Integer> bookPurchases = statsService.getPurchaseStats(statsService.getPurchaseByUserBetween(startTime, endTime));
        Map<String, Integer> ret = new HashMap<>();
        for(Map.Entry<Book, Integer> entry: bookPurchases.entrySet()) {
            ret.put(entry.getKey().getTitle(), entry.getValue());
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
