package com.nosorio.cars;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nosorio.cars.service.CarRepository;
import com.nosorio.cars.service.CarService;

@SpringBootApplication
public class Application
{
    @Autowired
    CarService carService;

    @Autowired
    CarRepository carRepository;
    
    @Value("classpath:cars.json")
    Resource resource;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadCars() throws JsonParseException, JsonMappingException, IOException {
        if (this.carRepository.count() == 0 && this.resource.exists()) {
        	logger.debug("Loading users from json file ...");
        	carService.loadCarsFromFile(this.resource.getInputStream());
        }
    }
}
