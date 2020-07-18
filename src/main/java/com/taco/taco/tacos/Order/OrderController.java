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
@Controller
@SessionAttributes("orderForm")
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

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
