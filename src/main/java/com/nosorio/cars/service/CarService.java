package com.nosorio.cars.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nosorio.cars.dtos.CarDto;

@Service
public class CarService {

	@Autowired
    CarRepository carRepository;
	
	@Autowired
    AddOnRepository addOnRepository;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public BigDecimal calculateValue(CarDto carDto) {

		BigDecimal finalValue = BigDecimal.ZERO;
		try {
			for(String addOn : carDto.getAddOns()) {
				log.info("Adding Add Ons Values");
				BigDecimal addOnValue = addOnRepository.getValues(addOn).get("VALUE");
				finalValue = finalValue.add(addOnValue);
			}
			BigDecimal carValue = carRepository.getValues(carDto.getName()).get("VALUE");
			log.info("Adding Start Car Value");
			finalValue = finalValue.add(carValue);
			log.info("Final value: {}", finalValue);
			return finalValue;
		}
		catch(Exception e) {
			return null;
		}
	}
}
