package com.cooksys.CommonInterests.entities;

import java.util.Set;

public class Person {
	private Long id;
	private String firstname;
	private String lastName;
	private Integer age;
	private Location location;
	private Set<Interest> interests;

	public Person(){
		// no-arg
	}
	
	public Person(String firstname, String lastName, Integer age, Location location, Set<Interest> interests) {
		super();
		this.firstname = firstname;
		this.lastName = lastName;
		this.age = age;
		this.id = null;
		this.location = location;
		this.interests = interests;
	}
	
	public Person(Long id, String firstname, String lastName, Integer age, Location location) {
		super();
		this.firstname = firstname;
		this.lastName = lastName;
		this.age = age;
		this.id = id;
		this.location = location;
		this.interests = null;
	}
	
	public Person(Long id, String firstname, String lastName, Integer age, Location location, Set<Interest> interests) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastName = lastName;
		this.age = age;
		this.location = location;
		this.interests = interests;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public Long getLocationId() {
		return location.getId();
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Set<Interest> getInterests() {
		return interests;
	}

	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Person)) {
			return false;
		}
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", firstname=" + firstname + ", lastName=" + lastName + ", age=" + age
				+ ", location=" + location + ", interests=" + interests + "]";
	}
}
