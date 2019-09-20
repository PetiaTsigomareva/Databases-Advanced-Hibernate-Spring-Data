package app.exam.config;

import app.exam.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public FileUtil fileUtil() {

        return new FileUtilImpl();
    }

    @Bean
    public Gson gson() {

        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                   .setPrettyPrinting().create();
    }

    @Bean
    public XmlParser xmlParser() {

        return new XmlParserImpl();
    }

    @Bean
    public ValidationUtil validationUtil() {

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
}
