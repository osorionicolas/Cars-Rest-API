package com.nosorio.cars.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nosorio.cars.models.AddOn;

@Repository
public interface AddOnRepository extends CrudRepository<AddOn, Long> {

	@Query(value = "SELECT * FROM ADD_ON WHERE NAME = :name", nativeQuery = true)
	Map<String, BigDecimal> getValues(@Param("name") String name);

}
