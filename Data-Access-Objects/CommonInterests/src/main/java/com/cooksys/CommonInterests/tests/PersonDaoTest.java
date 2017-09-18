package com.cooksys.CommonInterests.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cooksys.CommonInterests.dataAccessObject.LocationDao;
import com.cooksys.CommonInterests.dataAccessObject.PersonDao;
import com.cooksys.CommonInterests.entities.Interest;
import com.cooksys.CommonInterests.entities.Location;
import com.cooksys.CommonInterests.entities.Person;

public class PersonDaoTest {
	private static PersonDao personDao;
	private static Location location1;
	private static LocationDao locationDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
    	Connection conn = null;
    	try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "bondstone");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	locationDao = new LocationDao(conn);
    	personDao = new PersonDao(conn);
    	location1 = locationDao.get(new Long("1"));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetPersonById() {
      assertEquals(personDao.get(new Long("1")).toString(), "Person [id=1, firstname=Jeff, lastName=Sinatra, age=20, location=Location [id=1, city=New Orleans, state=LA, country=USA], interests=[Interest [id=1, title=Drama], Interest [id=2, title=Underwater Basket Weaving], Interest [id=3, title=Skydiving]]]");
	}
	
	@Test(expected = IOException.class)
	public void testExceptionOnBadId() throws IOException {
		personDao.savePerson(new Person(new Long("0"), "Bad", "Value", 100, location1, new HashSet<Interest>()));
	}
	
	

}
