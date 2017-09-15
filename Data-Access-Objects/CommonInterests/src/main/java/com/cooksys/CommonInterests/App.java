package com.cooksys.CommonInterests;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cooksys.CommonInterests.dataAccessObject.PersonDao;
import com.cooksys.CommonInterests.entities.Person;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        try {
			Class.forName("org.postgresql.Driver");
			
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "bondstone");
			
			
			
			
			
	        PersonDao personDao = new PersonDao(conn);
	        
	        System.out.println(personDao.getId(new Long("4")));

	        Person badId = new Person(new Long("100"), "fred", "sinatra", 5);
	        
	        try {
				personDao.savePerson(badId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Successfully caught Bad Id");
			}
	        
	        try {
	        	Person goodId = new Person(new Long("5"), "fred", "sinatra", 5);
				personDao.savePerson(goodId);
				System.out.println(personDao.getId(new Long("5")));
				Person changeBack = new Person(new Long("5"), "Brian", "Wilson", 24);
				personDao.savePerson(changeBack);
				System.out.println(personDao.getId(new Long("5")));
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        conn.close();
	        
//			Statement stmt = conn.createStatement();
//	        ResultSet rs = stmt.executeQuery("SELECT * FROM \"Person\"");
//			while(rs.next()){
//				System.out.println(rs.getString("firstName"));
//			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        
        
        
    }
}
