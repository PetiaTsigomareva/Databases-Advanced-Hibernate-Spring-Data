package com.petia.cardemo.config;

import com.petia.cardemo.util.ValidationUtil;
import com.petia.cardemo.util.ValidationUtilImpl;
import com.petia.cardemo.util.XmlParser;
import com.petia.cardemo.util.XmlParserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;


@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ValidationUtil validatorUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }
}

