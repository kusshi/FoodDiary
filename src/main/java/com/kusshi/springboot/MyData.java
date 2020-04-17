package com.kusshi.springboot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="mydata")
public class MyData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull	// ●
	private long id;

	@Column
	@NotEmpty	// ●
	private String foodName;

	@Column
	@NotNull	// ●
	private Integer foodCalorie;

	@Column
	@NotNull	// ●
	private Integer year;
	
	@Column
	@NotNull	// ●
	private Integer month;
	
	@Column
	@NotNull	// ●
	Integer dayOfMonth;

	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public Integer getFoodCalorie() {
		return foodCalorie;
	}
	public void setFoodCalorie(Integer foodCalorie) {
		this.foodCalorie = foodCalorie;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	

}
