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

	@Query(value = "SELECT * FROM ADDON_VALUES WHERE CODE = :code", nativeQuery = true)
	Map<String, BigDecimal> getValues(@Param("code") String code);

}
