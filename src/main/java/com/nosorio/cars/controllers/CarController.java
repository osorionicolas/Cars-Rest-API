package com.nosorio.cars.controllers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nosorio.cars.dtos.CarDto;
import com.nosorio.cars.models.Car;
import com.nosorio.cars.service.CarRepository;
import com.nosorio.cars.service.CarService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cars")
public class CarController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    CarService carService;
	
	@Autowired
    CarRepository carRepository;
	
	@ApiOperation(value = "List all cars",response = Iterable.class)
	@GetMapping(value = {"", "/"})
	public Iterable<Car> getAll()
	{
		log.debug("Accediendo a getAll()");
		return carRepository.findAll();
	}
	
	@ApiOperation(value = "Search a car by id",response = Car.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@GetMapping("/{id}")
	public ResponseEntity<Car> get(@PathVariable(value = "id") String carId) 
	{
		log.debug("Accediendo a get() con id = {}", carId);
		Optional<Car> dbCar = carRepository.findById(Long.parseLong(carId));
		if(dbCar.isPresent()) {
		    return ResponseEntity.ok().body(dbCar.get());
		}
		else {
	        return ResponseEntity.notFound().build();
		}
	}
	
	@ApiOperation(value = "Add a car",response = Car.class)
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a car"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 409, message = "The resource you were trying to create already exists")
    })
	@PostMapping(value = {"", "/"})
	public ResponseEntity<?> create(@RequestBody CarDto carDto) 
	{
		log.debug("Accediendo a create() con Car = {}", carDto.getId());
		if(!carRepository.findById(carDto.getId()).isPresent()) {
			Car car = carDto.toCar();
			BigDecimal value = this.carService.calculateValue(carDto);
			if(value == null) {
				return ResponseEntity.status(404).body("Car Can Not be Created");
			}
			car.setValue(value);
			carRepository.save(car);
		    return ResponseEntity.status(201).body(car);
		}
		else {
		    return ResponseEntity.status(409).body("Car ID Already Exists");
		}
	}
	
	@ApiOperation(value = "Update a car by id",response = Car.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated a car"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(value = "id") String carId, @RequestBody CarDto carDetails) {
		
		log.debug("Accediendo a update() con Car = {}", carDetails.toString());
		if(carDetails.getName() == null || carDetails.getAddOns() == null) {
		    return ResponseEntity.status(403).body("Request Must Have Name and Add Ons");
		}
		else if(carRepository.findById(Long.parseLong(carId)).isPresent()) {
			BigDecimal value = this.carService.calculateValue(carDetails);
			if(value == null) {
				return ResponseEntity.status(404).body("Car Can Not be Updated");
			}
			Car car = carDetails.toCar();
			car.setId(Long.parseLong(carId));
			car.setValue(value);	    
		    Car updatedCar = carRepository.save(car);
		    return ResponseEntity.ok(updatedCar);
		}
		else {
	        return ResponseEntity.notFound().build();
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "Update especified car attributes by id",response = Car.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated a car"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
	@PatchMapping("/{id}")
	public ResponseEntity<?> patch(@PathVariable(value = "id") String carId, @RequestBody(required=false) Map<String, Object> changes) {
		Optional<Car> dbCar = carRepository.findById(Long.parseLong(carId));
		if(changes == null || changes.isEmpty()) {
		    return ResponseEntity.status(403).body("Parameter Changes is not set");
		}
		else if(dbCar.isPresent()) {
			log.info("Accediendo a update() con deltas = {}", changes.toString());
			CarDto carDto = new CarDto();
			carDto.setName( (changes.containsKey("name")) ?	(String) changes.get("name") : dbCar.get().getName());
			carDto.setAddOns( (changes.containsKey("addOns")) ?	(List<String>) changes.get("addOns") : Arrays.asList(dbCar.get().getAddOns().split(";")));
			log.info(dbCar.get().getAddOns());
		    BigDecimal value = this.carService.calculateValue(carDto);
			if(value == null) {
				return ResponseEntity.status(409).body("Car Can Not be Updated");
			}
			Car car = carDto.toCar();
			car.setId(Long.parseLong(carId));
			car.setValue(value);
		    Car updatedCar = carRepository.save(car);
		    return ResponseEntity.ok(updatedCar);
		}
		else {
		    return ResponseEntity.notFound().build();
		}
	}
	
	@ApiOperation(value = "Delete a car by id")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
    })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") String carId)
	{
		log.debug("Accediendo a delete() con car = {}", carId);
		Optional<Car> car =  carRepository.findById(Long.parseLong(carId));
		if(car.isPresent()) {
		    carRepository.delete(car.get());
		    return ResponseEntity.ok().build();
		}
		else {
	        return ResponseEntity.notFound().build();
		}
	}

}
