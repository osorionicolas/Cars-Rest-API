package com.nosorio.cars.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosorio.cars.dtos.CarDto;
import com.nosorio.cars.models.Car;

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
				log.info("Adding Add On Value for {}", addOn);
				BigDecimal addOnValue = Optional.ofNullable(addOnRepository.getValues(addOn).get("VALUE")).orElse(BigDecimal.ZERO);
				finalValue = finalValue.add(addOnValue);
			}
			BigDecimal carValue = Optional.ofNullable(carRepository.getValues(carDto.getName()).get("VALUE")).orElse(BigDecimal.ZERO);
			log.info("Adding Start Car Value");
			finalValue = finalValue.add(carValue);
			log.info("Final value: {}", finalValue);
			return finalValue;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
    public void loadCarsFromFile(InputStream inputStream) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Car> cars = objectMapper.readValue(inputStream, new TypeReference<List<Car>>(){});
        
        for (Car car : cars) {
            log.debug("Reading car {}", car.getName());
            carRepository.save(car);
        }
    }
}
