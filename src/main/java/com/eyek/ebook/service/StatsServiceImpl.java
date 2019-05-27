package com.eyek.ebook.service;

import com.eyek.ebook.dao.OrderDao;
import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StatsServiceImpl implements StatsService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private AuthenticationFacade authenticationFacade;


    @Override
    public Map<Book, Integer> getPurchaseStats(List<Order> orders) {
        Map<Book, Integer> ret = new HashMap<>();
        for(Order order: orders) {
            for(OrderItem item: order.getOrderItems()) {
                ret.put(item.getBook(), ret.getOrDefault(item.getBook(), 0) + item.getAmount());
            }
        }
        return ret;
    }

    @Override
    public List<Order> getPurchaseByUserBetween(Date startTime, Date endTime) {
        return orderDao.getOrderByUserBetween(authenticationFacade.getCurrentUser(), startTime, endTime);
    }

}
