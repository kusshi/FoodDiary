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
	@NotEmpty	// ●
	private String time;

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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	

}
