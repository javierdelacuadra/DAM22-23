package com.example;

public class Employee {

	private Long id;
	private String name;
	private String nif;
	private Integer age;
	public Employee() {
	}
	public Employee(Long id, String name, String nif, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.nif = nif;
		this.age = age;
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
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", nif=" + nif + ", age=" + age + "]";
	}
	
	
	
	
}
