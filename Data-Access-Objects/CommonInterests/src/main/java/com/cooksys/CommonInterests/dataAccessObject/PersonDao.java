package com.cooksys.CommonInterests.dataAccessObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.cooksys.CommonInterests.entities.Interest;
import com.cooksys.CommonInterests.entities.Location;
import com.cooksys.CommonInterests.entities.Person;

public class PersonDao {
	private Statement st;
	private LocationDao locationDao;
	private InterestDao interestDao;
	
	public PersonDao(){
		// no-arg
	}
	
	public PersonDao(Connection conn){
		try {
			this.st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public PersonDao(Connection conn, LocationDao locationDao, InterestDao interestDao){
		try {
			this.st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.locationDao = locationDao;
		this.interestDao = interestDao;
	}
	
	public Person get(Long id){
		if (st == null){
			System.out.println("Statement null in PersonDao getId method. Execution failed");
			return null;
		}
		
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM \"Person\" WHERE id='" + id + "'");
			rs.next();
			Location location = locationDao.get(rs.getLong("location_id"));
			Person person = new Person(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getInt("age"), location);
			Set<Interest> interests = interestDao.get(person);
			person.setInterests(interests);
			return person;
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
		
		String query;
		try {
			if (person.getId() == null){
				query = "INSERT INTO \"Person\" (\"firstName\", \"lastName\", \"age\", \"location_id\") VALUES ";
				query += String.format("('%s', '%s', '%d', '%d')", person.getFirstname(), person.getLastName(), person.getAge(), person.getLocationId());
				st.execute(query, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = st.getGeneratedKeys();
				rs.next();
				person.setId(rs.getLong("id"));
				for (Interest i : person.getInterests()){
					query = "INSERT INTO \"PersonInterests\" (person_id, interest_id) VALUES ";
					query += String.format("('%d', '%d')", person.getId(), i.getId());
					st.executeUpdate(query);
				}
				return person;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			query = "UPDATE \"Person\" SET ";
			query += String.format("\"firstName\"='%s', \"lastName\"='%s', \"age\"='%d', \"location_id\"='%d'", person.getFirstname(), person.getLastName(), person.getAge(), person.getLocationId());
			query += "WHERE id = " + person.getId();
			Integer modifiedCount = st.executeUpdate(query);
			if (modifiedCount < 1)
				throw new IOException();
			st.executeUpdate("DELETE FROM \"PersonInterests\" WHERE person_id = " + person.getId());
			for (Interest i : person.getInterests()){
				query = "INSERT INTO \"PersonInterests\" (person_id, interest_id) VALUES ";
				query += String.format("('%d', '%d')", person.getId(), i.getId());
				st.executeUpdate(query);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return person;
	}
	
	public Person removePerson(Person person){
		try {
			st.executeUpdate("DELETE FROM \"PersonInterests\" WHERE person_id = " + person.getId());
			st.executeUpdate("DELETE FROM \"Person\" WHERE id='" + person.getId() + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return person;
	}
	
	public Set<Person> findInterestGroup(Interest interest, Location loc){
		if (st == null){
			System.out.println("Statement null in PersonDao findInterestGroup method. Execution failed");
			return null;
		}
		HashSet<Person> personSet = new HashSet<Person>();
		
		StringBuilder query = new StringBuilder();
		query.append("select * from \"Person\" ");
		query.append("join \"Locations\" ");
		query.append("on \"Person\".\"location_id\" = \"Locations\".\"id\" ");
		query.append("join \"PersonInterests\" ");
		query.append("on \"Person\".\"id\" = \"PersonInterests\".\"person_id\" ");
		query.append("join \"Interests\" ");
		query.append("on \"PersonInterests\".\"interest_id\" = \"Interests\".\"id\" ");
		query.append("where \"location_id\" = " + loc.getId() + " and \"interest_id\" = " + interest.getId() + "");
		
		try {
			ResultSet rs = st.executeQuery(query.toString());
			while (rs.next()){
				Location location = locationDao.get(rs.getLong("location_id"));
				personSet.add(new Person(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getInt("age"), location));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return personSet;
	}               
	


}
