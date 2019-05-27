package com.eyek.ebook.service;

import com.eyek.ebook.dao.BookDao;
import com.eyek.ebook.dao.OrderDao;
import com.eyek.ebook.dao.OrderItemDao;
import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.model.Book;
import com.eyek.ebook.model.Order;
import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.model.User;
import com.eyek.ebook.util.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Positive;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ebook.paging.pageSize}")
    private Integer defaultPageSize;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Override
    public boolean addOrderItem(@Positive int bookId, @Positive int amount) {
        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(amount);
        orderItem.setBook(bookDao.getOne(bookId));
        return orderDao.addCartItem(getCurrentCartOrder(), orderItem);
    }

    @Override
    public boolean modifyOrderItem(@Positive int orderItemId, @Positive int amount) {
        OrderItem orderItem = orderItemDao.getOrderItem(orderItemId);
        orderItem.setAmount(amount);
        orderItemDao.saveOrderItem(orderItem);
        return true;
    }

    @Override
    public boolean deleteOrderItem(@Positive int orderItemId) {
        return orderItemDao.deleteOrderItem(orderItemId);
    }

    @Override
    public Order getCurrentCartOrder() {
        User user = authenticationFacade.getCurrentUser();
        if(user == null)
            return null;
        Order order = orderDao.getCartOrder(user);
        if(order == null)
            order = orderDao.createCartOrder(user);
        return order;
    }

    @Override
    public Page<OrderItem> getCurrentCartItems(int pageNumber) {
        return orderDao.getOrderItems(getCurrentCartOrder(), pageNumber, defaultPageSize);
    }

    @Override
    public boolean deleteCurrentCartOrder() {
        User user = authenticationFacade.getCurrentUser();
        return orderDao.deleteCartOrder(user);
    }

    @Override
    @Transactional
    public boolean submitCurrentCartOrder() throws OutOfStockException {
        Order order = getCurrentCartOrder();
        if(order != null) {
            // check storage
            for(OrderItem orderItem: order.getOrderItems()) {
                Book book = orderItem.getBook();
                if(orderItem.getAmount() <= book.getStock()) {
                    book.setStock(book.getStock() - orderItem.getAmount());
                } else {
                    // out of stock
                    throw new OutOfStockException(orderItem);
                }
            }
            order.setStatus(Order.OrderStatus.submitted);
            orderDao.saveOrder(order);
            return true;
        }
        return false;
    }

    @Override
    public Page<Order> getCurrentUserOrders(int pageNumber) {
        return getOrdersByUser(authenticationFacade.getCurrentUser(), pageNumber);
    }

    @Override
    public Page<Order> getOrdersByUser(User user, int pageNumber) {
        return orderDao.getOrders(user, pageNumber, defaultPageSize);
    }

    @Override
    public Page<Order> getOrdersByBook(Book book, int pageNumber) {
        return orderDao.getOrdersByBook(book, pageNumber, defaultPageSize);
    }

    @Override
    public Page<Order> getOrdersByBook(int bookId, int pageNumber) {
        return orderDao.getOrdersByBook(bookDao.getOne(bookId), pageNumber, defaultPageSize);
    }

    @Override
    public Page<Order> getAllOrders(int pageNumber) {
        return orderDao.getAllOrders(pageNumber, defaultPageSize);
    }
}
