package com.cooksys.CommonInterests.dataAccessObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cooksys.CommonInterests.entities.Person;

public class PersonDao {
	private Connection conn;
	private Statement st;
	
	public PersonDao(Connection conn){
		this.conn = conn;
		try {
			this.st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Person getId(Long id){
		if (st == null){
			System.out.println("Statement null in PersonDao getId method. Execution failed");
			return null;
		}
		
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM \"Person\" WHERE id='" + id + "'");
			rs.next();
			return new Person(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getInt("age"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Person savePerson(Person person) throws IOException{
		if (st == null){
			System.out.println("Statement null in PersonDao savePerson method. Execution failed");
			return null;
		}
		
		Long id = person.getId();
		
		try {

			Integer modifiedCount = st.executeUpdate("UPDATE \"Person\" SET \"firstName\"='" + person.getFirstname() + "', \"lastName\"='" + person.getLastName() + "', age='" + person.getAge() + "' WHERE id='" + person.getId() + "'");
			if (modifiedCount < 1)
				throw new IOException();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return person;
	}
	


}
