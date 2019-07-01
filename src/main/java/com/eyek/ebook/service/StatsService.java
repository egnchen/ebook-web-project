package com.eyek.ebook.service;

import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatsService {

    public Map<Book, Integer> getPurchaseStats(List<Order> orders);
    public List<Order> getPurchaseByUserBetween(Date startTime, Date endTime, User user);
    public List<Order> getAllPurchasesBetween(Date startTime, Date endTime);
    public List<Order> getAllPurchasesForBook(Book book);
}
