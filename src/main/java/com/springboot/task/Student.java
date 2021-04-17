package com.springboot.task;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="students")
public class Student extends AuditModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max=100)
	@Column(name="first_name")
	private String firstName;
	
	@NotNull
	@Size(max=100)
	@Column(name="last_name")
	private String lastName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	
}
