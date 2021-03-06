package edu.miu.cs545.group5.onlinemarket.service.impl;

import edu.miu.cs545.group5.onlinemarket.domain.Order;
import edu.miu.cs545.group5.onlinemarket.repository.OrderRepository;
import edu.miu.cs545.group5.onlinemarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order getOrderByBuyerId(Long id) {
//        return orderRepository.findByBuyerId(id).orElse(null);
        List<Order> orders = orderRepository.findAllByBuyerId(id);
        if (orders.isEmpty()) {
            return new Order();
        } else {
            return orders.get(orders.size() - 1);
        }
    }

    @Override
    public List<Order> getOrderHistoryByBuyerId(Long id) {
        return orderRepository.findAllByBuyerId(id);
    }

    @Override
    public Page<Order> findPageableOrderBySellerId(Pageable pageable, Long id) {
        return orderRepository.findBySellerId(pageable,id);
    }

    @Override
    public void cancelOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public int updateOrderStatusByOrderId(Long orderId, String status) {
        return orderRepository.setFixedStatus(orderId,status);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

}
