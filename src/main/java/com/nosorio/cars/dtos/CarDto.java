package com.nosorio.cars.dtos;

import java.util.List;

import com.nosorio.cars.models.Car;

public class CarDto {

	private Long id;
	private String name;
	private List<String> addOns;
	
	public CarDto() {
	}
	
	public CarDto(String id, String name, List<String> addOns) {
		this.id = Long.parseLong(id);
		this.name = name;
		this.addOns = addOns;
	}
	
	public Car toCar() {
		Car car = new Car();
		car.setId(this.id);
		car.setAddOns(this.addOns.toString());
		car.setName(this.name);
		return car;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAddOns() {
		return addOns;
	}

	public void setAddOns(List<String> addOns) {
		this.addOns = addOns;
	}
}
