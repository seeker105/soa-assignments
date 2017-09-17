package com.cooksys.CommonInterests.dataAccessObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.cooksys.CommonInterests.entities.Interest;
import com.cooksys.CommonInterests.entities.Person;

public class InterestDao {
	Statement st;
	
	public InterestDao(){
		// no-arg
	}

	public InterestDao(Connection conn){
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Set<Interest> get(Person person){		
		if (st == null){
			System.out.println("Statement null in InterestDao get method. Execution failed");
			return null;
		}
		HashSet<Interest> interest = new HashSet<Interest>();
		
		try {
			String query = "SELECT * FROM \"Interests\" JOIN \"PersonInterests\"";
			query += "ON \"PersonInterests\".interest_id = \"Interests\".id ";
			query += "JOIN \"Person\" ";
			query += "ON \"PersonInterests\".person_id = \"Person\".id ";
			query += "WHERE \"Person\".id = " + person.getId();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				interest.add(new Interest(rs.getLong("interest_id"), rs.getString("title")));			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return interest;
	}
	
	
	public Interest get(Long id){		
		if (st == null){
			System.out.println("Statement null in InterestDao get method. Execution failed");
			return null;
		}
		Interest interest = null;
		
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM \"Interests\" WHERE id=" + id);
			rs.next();
			interest = new Interest(rs.getLong("id"), rs.getString("title"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return interest;
	}
	
	
	
	public Interest saveInterest(Interest interest) throws IOException{
		if (st == null){
			System.out.println("Statement null in InterestDao saveInterest method. Execution failed");
			return null;
		}	
		
		try {
			if (interest.getId() == null){
				String query = "INSERT INTO \"Interests\" (\"title\") VALUES ('" + interest.getTitle() +"')";
				st.execute(query, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = st.getGeneratedKeys();
				rs.next();
				interest.setId(rs.getLong("id"));
				return interest;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String query = "UPDATE \"Interests\" SET ";
			query += String.format("\"title\"='%s' ", interest.getTitle());
			query += String.format("WHERE id='%s'", interest.getId());
			Integer modifiedCount = st.executeUpdate(query);
			if (modifiedCount < 1)
				throw new IOException();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return interest;
	}
	
	public Interest removeInterest(Interest interest){
		try {
			st.executeUpdate("DELETE FROM \"Interests\" WHERE id='" + interest.getId() + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return interest;
	}
}
