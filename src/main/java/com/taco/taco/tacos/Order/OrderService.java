package com.taco.taco.tacos.Order;

import com.taco.taco.tacos.User.Account;
import com.taco.taco.tacos.taco.TacoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TacoRepository tacoRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void processNewOrder(OrderForm orderForm, Account account) {
        Order order = modelMapper.map(orderForm, Order.class);
        order.getTacos().forEach(tacoRepository::save);
        order.setAccount(account);
        orderRepository.save(order);
    }
}
