package com.taco.taco.tacos.Order;

import com.taco.taco.tacos.Order.Order;
import com.taco.taco.tacos.Order.OrderForm;
import com.taco.taco.tacos.Order.OrderRepository;
import com.taco.taco.tacos.User.Account;
import com.taco.taco.tacos.User.CurrentUser;
import com.taco.taco.tacos.User.UserAccount;
import com.taco.taco.tacos.taco.Taco;
import com.taco.taco.tacos.taco.TacoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.hibernate.sql.ordering.antlr.OrderByFragment;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "taco.orders")
@SessionAttributes("orderForm")
@RequestMapping("/orders")
@Controller
public class OrderController {

    private final OrderService orderService;
    private int pageSize = 20;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @GetMapping
    public String ordersForUser(@CurrentUser Account account, Model model) {
        Pageable pageable = PageRequest.of(0, 20);
        model.addAttribute("orders", orderService.findByAccountOrderByPlacedAtDesc(account, pageable));

        return "orderList";
    }

    @GetMapping("/current")
    public String orderForm(@CurrentUser Account account, @ModelAttribute OrderForm orderForm) {
        if(orderForm.getDeliveryName() == null) {
            orderForm.setDeliveryName(account.getFullname());
        }
        if(orderForm.getDeliveryStreet() == null) {
            orderForm.setDeliveryStreet(account.getStreet());
        }
        if(orderForm.getDeliveryCity() == null) {
            orderForm.setDeliveryCity(account.getCity());
        }
        if(orderForm.getDeliveryState() == null) {
            orderForm.setDeliveryState(account.getState());
        }
        if(orderForm.getDeliveryZip() == null) {
            orderForm.setDeliveryZip(account.getZip());
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid OrderForm orderForm, Errors errors, SessionStatus sessionStatus,
                               @CurrentUser Account account){
        if(errors.hasErrors()) {
            return "orderForm";
        }
        orderService.processNewOrder(orderForm, account);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
