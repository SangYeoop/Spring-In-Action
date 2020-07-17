package com.taco.taco.tacos.config;

import com.taco.taco.tacos.Order.Order;
import com.taco.taco.tacos.Order.OrderForm;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
        return modelMapper;
    }
}
