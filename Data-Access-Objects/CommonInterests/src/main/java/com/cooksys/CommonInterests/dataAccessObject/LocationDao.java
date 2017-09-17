package com.cooksys.CommonInterests.dataAccessObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cooksys.CommonInterests.entities.Location;
import com.cooksys.CommonInterests.entities.Person;

public class LocationDao {
	Statement st;
	
	public LocationDao(){
		// no-arg
	}
	
	public LocationDao(Connection conn){
		st = null;
		
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Location get(Person person){		
		if (st == null){
			System.out.println("Statement null in LocationDao get method. Execution failed");
			return null;
		}
		Location loc = null;
		
		try {
			String query = "SELECT location_id, city, state, country FROM \"Locations\" JOIN ";
			query += "\"Person\" ON ";
			query += "\"Person\".location_id = \"Locations\".id ";
			query += "WHERE \"Person\".id = " + person.getId();
			
			
			ResultSet rs = st.executeQuery(query);
			rs.next();
			loc = new Location(rs.getLong("location_id"), rs.getString("city"), rs.getString("state"), rs.getString("country"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loc;
	}
	
	
	public Location get(Long id){		
		if (st == null){
			System.out.println("Statement null in LocationDao get method. Execution failed");
			return null;
		}
		Location loc = null;
		
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM \"Locations\" WHERE id=" + id);
			rs.next();
			loc = new Location(rs.getLong("id"), rs.getString("city"), rs.getString("state"), rs.getString("country"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loc;
	}
	
	
	
	public Location saveLocation(Location location) throws IOException{
		if (st == null){
			System.out.println("Statement null in LocationDao saveLocation method. Execution failed");
			return null;
		}	
		
		try {
			if (location.getId() == null){
				String query = "INSERT INTO \"Locations\" (\"city\", \"state\", \"country\") VALUES ";
				query += String.format("('%s', '%s', '%s')", location.getCity(), location.getState(), location.getCountry());
				st.execute(query, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = st.getGeneratedKeys();
				rs.next();
				location.setId(rs.getLong("id"));
				return location;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String query = "UPDATE \"Locations\" SET ";
			query += String.format("\"city\"='%s', \"state\"='%s', \"country\"='%s' ", location.getCity(), location.getState(), location.getCountry());
			query += String.format("WHERE id='%s'", location.getId());
			Integer modifiedCount = st.executeUpdate(query);
			if (modifiedCount < 1)
				throw new IOException();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
	}
	
	public Location removeLocation(Location location){
		try {
			st.executeUpdate("DELETE FROM \"Locations\" WHERE id='" + location.getId() + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
	}

}
