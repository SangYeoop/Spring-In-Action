package com.taco.taco.tacos.web;

import com.taco.taco.tacos.Order.Order;
import com.taco.taco.tacos.Order.OrderForm;
import com.taco.taco.tacos.Order.OrderRepository;
import com.taco.taco.tacos.taco.Taco;
import com.taco.taco.tacos.taco.TacoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.ordering.antlr.OrderByFragment;
import org.modelmapper.ModelMapper;
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

    private final TacoRepository tacoRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/current")
    public String orderForm(Model model) {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid OrderForm orderForm, Errors errors, SessionStatus sessionStatus){
        if(errors.hasErrors()) {
            return "orderForm";
        }
        Order order = modelMapper.map(orderForm, Order.class);
        order.getTacos().forEach(tacoRepository::save);
        orderRepository.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
