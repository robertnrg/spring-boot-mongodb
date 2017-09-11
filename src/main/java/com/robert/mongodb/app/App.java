package com.robert.mongodb.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ "com.robert.mongodb.*" })
@EnableMongoRepositories({ "com.robert.mongodb.repository" })
public class App {

   public static void main(String[] args) {
      SpringApplication.run(App.class, args);
   }

}