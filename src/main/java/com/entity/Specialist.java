package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Specialist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	private String specialistName;


	public Specialist() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Specialist(int id, String specialistName) {
		super();
		this.id = id;
		this.specialistName = specialistName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSpecialistName() {
		return specialistName;
	}

	public void setSpecialistName(String specialistName) {
		this.specialistName = specialistName;
	}

}
