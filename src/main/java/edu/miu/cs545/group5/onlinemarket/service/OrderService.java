package edu.miu.cs545.group5.onlinemarket.service;

import edu.miu.cs545.group5.onlinemarket.domain.Order;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrderService {
    void save(Order order);
    Order getOrderByBuyerId(Long id);

    List<Order> getOrderHistoryByBuyerId(Long id);

    public Page<Order> findPageableOrderBySellerId(Pageable pageable, Long id);
    public void cancelOrderById(Long orderId);
    public int updateOrderStatusByOrderId(Long orderId, String status);

    Order getOrderById(Long id);
}
