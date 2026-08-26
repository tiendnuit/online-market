package edu.miu.cs545.group5.onlinemarket.controller;

import edu.miu.cs545.group5.onlinemarket.config.ImageUtil;
import edu.miu.cs545.group5.onlinemarket.config.OrderMapper;
import edu.miu.cs545.group5.onlinemarket.domain.Order;
import edu.miu.cs545.group5.onlinemarket.domain.Seller;
import edu.miu.cs545.group5.onlinemarket.domain.pojo.OrderPOJO;
import edu.miu.cs545.group5.onlinemarket.service.OrderService;
import edu.miu.cs545.group5.onlinemarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/seller/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping("/orderList/{page}")
    public ModelAndView getOrderBySellerId(@PathVariable("page")int page){
        ModelAndView modelAndView = new ModelAndView("manageOrder");
        PageRequest pageable = PageRequest.of(page - 1, 15);

        //Only login Seller do this
        Seller seller = (Seller) userService.getLoggedUser().get();

        Page<Order> orderPage = orderService.findPageableOrderBySellerId(pageable,userService.getLoggedUser().get().getId());
        int totalPages = orderPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        List<String> status = new ArrayList<>();
        status.add("NEW");
        status.add("PAID");
        status.add("SHIPPED");
        status.add("CANCELED");
        List<Order> orderLines = orderPage.getContent();
        List<OrderPOJO> orderPOJOList = OrderMapper.mapOder(orderPage.getContent());
        modelAndView.addObject("pId", userService.getLoggedUser().get().getId());
        modelAndView.addObject("statusList", status);
        modelAndView.addObject("seller",seller);


        modelAndView.addObject("activeProductList", true);
        modelAndView.addObject("orderList", orderPOJOList);
        modelAndView.addObject("imgUtil", new ImageUtil());
        // I added this comment
        return modelAndView;
    }

    @RequestMapping("/changeStatus/{orderId}/{status}")
    public String changeOrderStatus(@PathVariable("orderId")Long orderId, @PathVariable("status") String status){
        orderService.updateOrderStatusByOrderId(orderId,status);
        return "manageOrder";
    }

    @RequestMapping("/cancelOrder/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrderById(orderId);
        return "manageOrder";
    }
}
