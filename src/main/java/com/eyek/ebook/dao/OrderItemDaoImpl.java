package com.eyek.ebook.dao;

import com.eyek.ebook.model.OrderItem;
import com.eyek.ebook.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemDaoImpl implements OrderItemDao {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem getOrderItem(int id) {
        return orderItemRepository.getOne(id);
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public boolean deleteOrderItem(int id) {
        if(orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
