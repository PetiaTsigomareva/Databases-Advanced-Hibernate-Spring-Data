package com.petia.cardemo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.petia.cardemo.util.FileIOUtil;
import com.petia.cardemo.util.FileIOUtilImpl;
import com.petia.cardemo.util.ValidatorUtil;
import com.petia.cardemo.util.ValidatorUtilImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileIOUtil fileIOUtil() {
        return new FileIOUtilImpl();
    }

    @Bean
    public Gson gson() {

        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
       // return new GsonBuilder().serializeSpecialFloatingPointValues().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

    @Bean
    public ValidatorUtil validatorUtil() {
        return new ValidatorUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}

