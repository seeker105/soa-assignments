package com.cooksys.CommonInterests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cooksys.CommonInterests.dataAccessObject.LocationDao;
import com.cooksys.CommonInterests.dataAccessObject.PersonDao;



import org.junit.Test;



/**
 * Unit test for simple App.
 */
//public class AppTest 
//    extends TestCase
//{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
//    public AppTest( String testName )
//    {
//        super( testName );
//    }

    /**
     * @return the suite of tests being tested
     */
//    public static Test suite()
//    {
//    	Connection conn = null;
//    	try {
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "bondstone");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//    	
//    	LocationDao locationDao = new LocationDao(conn);
//    	PersonDao personDao = new PersonDao(conn);
//        return new TestSuite( AppTest.class );
//    }

    /**
     * Rigourous Test :-)
     */
//    @Test
//    public void case1(){
//    	
//    }
    
//    public void testApp()
//    {
//    	Connection conn = null;
//    	try {
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "bondstone");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//    	
//    	PersonDao personDao = new PersonDao(conn);
//    	LocationDao locationDao = new LocationDao(conn);
//
//        assertEquals(personDao.get(new Long("1")).toString(), "Person [id=1, firstname=Jeff, lastName=Sinatra, age=20, location=Location [id=1, city=New Orleans, state=LA, country=USA], interests=[Interest [id=1, title=Drama], Interest [id=2, title=Underwater Basket Weaving], Interest [id=3, title=Skydiving]]]");
//        
//        
//        
//        
//        
//        try {
//			conn.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
    
//}
