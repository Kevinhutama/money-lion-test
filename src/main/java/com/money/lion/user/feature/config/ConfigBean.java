package com.money.lion.user.feature.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBean {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
