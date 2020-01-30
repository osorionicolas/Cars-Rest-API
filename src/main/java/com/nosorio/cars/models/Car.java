package com.nosorio.cars.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Car {

	@Id
	private Long id;
	@NotNull
	private BigDecimal value;
	@NotNull
	private String name;
	private String addOns;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddOns() {
		return addOns;
	}
	public void setAddOns(String addOns) {
		this.addOns = addOns;
	}

	public String toString()
	{
		ObjectMapper objectMapper = new ObjectMapper();
		try
		{
			return objectMapper.writeValueAsString(this);
		}
		catch(JsonProcessingException jpe)
		{
			throw new RuntimeException(jpe);
		}
	}
}
