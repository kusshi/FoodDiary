package com.kusshi.springboot.repositories;

import com.kusshi.springboot.MyData;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyDataRepository extends JpaRepository<MyData, Long> {

	public Optional<MyData> findById(Long id);
	public List<MyData> findByFoodCalorieGreaterThan(Integer thresholdCalorie);
}