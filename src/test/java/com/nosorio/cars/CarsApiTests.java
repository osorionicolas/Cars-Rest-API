package com.nosorio.cars;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosorio.cars.controllers.CarController;
import com.nosorio.cars.dtos.CarDto;
import com.nosorio.cars.service.AddOnRepository;
import com.nosorio.cars.service.CarRepository;
import com.nosorio.cars.service.CarService;

//@RunWith(SpringRunner.class)
//@WebMvcTest({CarController.class, CarService.class})
public class CarsApiTests {

	/*@Autowired
    private MockMvc mockMvc;
	
    @MockBean
    private CarRepository carRepository;
    
    @MockBean
    private AddOnRepository addOnRepository;
  
	@Test
	public void create() throws Exception {
		List<String> addOnsList = new ArrayList<String>();
		Collections.addAll(addOnsList, new String[] {"AA"});
		CarDto carDto = new CarDto("1", "toyota", addOnsList);
		mockMvc.perform(MockMvcRequestBuilders.post("/cars")
												 .content(asJsonString(carDto))
												 .contentType(MediaType.APPLICATION_JSON)
												 .accept(MediaType.APPLICATION_JSON))
												 .andExpect(status().isCreated());
												 //.andExpect(MockMvcResultMatchers.jsonPath("$.value").value("265000"));
	}
	
	@Test
	public void getAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
						  					  .andDo(MockMvcResultHandlers.print())
						  					  .andExpect(MockMvcResultMatchers.status().isOk());
						  					  //.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
	}
	
	@Test
	public void delete() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/cars/1"))
						  					  .andExpect(status().isOk())
						  					  .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	    	String json = new ObjectMapper().writeValueAsString(obj);
	    	System.out.println(json);
	        return json;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}*/
}
  