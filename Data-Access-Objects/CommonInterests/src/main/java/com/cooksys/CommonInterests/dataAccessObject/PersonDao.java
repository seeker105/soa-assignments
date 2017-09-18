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
			this.locationDao = new LocationDao(conn);
			this.interestDao = new InterestDao(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	public Person get(Long id){
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM \"Person\" WHERE id='" + id + "'");
			rs.next();
			Location location = locationDao.get(rs.getLong("location_id"));
			Person person = new Person(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getInt("age"), location);
			Set<Interest> interests = interestDao.get(person);
			person.setInterests(interests);
			return person;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Person savePerson(Person person) throws IOException{
		if (person.getId() == null){
			return insertPersonIntoDb(person);
		}
		return updatePersonInDb(person);  // throws IOException if person's ID is not in the Database
	}
	
	public Person removePerson(Person person){
		try {
			st.executeUpdate("DELETE FROM \"PersonInterests\" WHERE person_id = " + person.getId());
			st.executeUpdate("DELETE FROM \"Person\" WHERE id='" + person.getId() + "'");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return person;
	}
	
	public Set<Person> findInterestGroup(Interest interest, Location location){
		HashSet<Person> personSet = new HashSet<Person>();
		try {
			String query = buildFindInterestGroupQuery(interest, location);
			ResultSet rs = st.executeQuery(query.toString());
			HashSet<Long> personIdSet = new HashSet<Long>();
			while (rs.next()){
				personIdSet.add(rs.getLong("person_id"));
			}
			// Must process first ResultSet object fully before creating a new ResultSet in the `get` method
			for (Long id : personIdSet){
				personSet.add(get(id));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return personSet;
	}
	
	private void putPersonInterestsInDb(Person person){
		try {
			String query;
			st.executeUpdate("DELETE FROM \"PersonInterests\" WHERE person_id = " + person.getId());
			for (Interest i : person.getInterests()){
				query = "INSERT INTO \"PersonInterests\" (person_id, interest_id) VALUES ";
				query += String.format("('%d', '%d')", person.getId(), i.getId());
				st.executeUpdate(query);
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String buildFindInterestGroupQuery(Interest interest, Location location){
		StringBuilder query = new StringBuilder();
		query.append("select * from \"Person\" ");
		query.append("join \"Locations\" ");
		query.append("on \"Person\".\"location_id\" = \"Locations\".\"id\" ");
		query.append("join \"PersonInterests\" ");
		query.append("on \"Person\".\"id\" = \"PersonInterests\".\"person_id\" ");
		query.append("join \"Interests\" ");
		query.append("on \"PersonInterests\".\"interest_id\" = \"Interests\".\"id\" ");
		query.append("where \"location_id\" = " + location.getId() + " and \"interest_id\" = " + interest.getId() + "");
		return query.toString();
	}
	
	private Person insertPersonIntoDb(Person person){
		try {
			String query = "INSERT INTO \"Person\" (\"firstName\", \"lastName\", \"age\", \"location_id\") VALUES ";
			query += String.format("('%s', '%s', '%d', '%d')", person.getFirstname(), person.getLastName(), person.getAge(), person.getLocationId());
			st.execute(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			person.setId(rs.getLong("id"));
			putPersonInterestsInDb(person);
			return person;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	private Person updatePersonInDb(Person person) throws IOException {
		try {
			String query = "UPDATE \"Person\" SET ";
			query += String.format("\"firstName\"='%s', \"lastName\"='%s', \"age\"='%d', \"location_id\"='%d'", person.getFirstname(), person.getLastName(), person.getAge(), person.getLocationId());
			query += "WHERE id = " + person.getId();
			Integer modifiedCount;
			modifiedCount = st.executeUpdate(query);
			if (modifiedCount < 1)
				throw new IOException();
			putPersonInterestsInDb(person);
			return person;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	


}
