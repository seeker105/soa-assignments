package com.cooksys.CommonInterests;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.cooksys.CommonInterests.dataAccessObject.InterestDao;
import com.cooksys.CommonInterests.dataAccessObject.LocationDao;
import com.cooksys.CommonInterests.dataAccessObject.PersonDao;
import com.cooksys.CommonInterests.entities.Interest;
import com.cooksys.CommonInterests.entities.Location;
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
			
	        LocationDao locationDao = new LocationDao(conn);
	        InterestDao interestDao = new InterestDao(conn);
	        PersonDao personDao = new PersonDao(conn);
	        Location location1 = locationDao.get(new Long("1"));
	        Location location2 = locationDao.get(new Long("2"));
	        
	        Interest interest1 = interestDao.get(new Long("1"));
	        Interest interest2 = interestDao.get(new Long("2"));
	        Interest interest3 = interestDao.get(new Long("3"));
	        Interest interest4 = interestDao.get(new Long("4"));
	        Interest interest5 = interestDao.get(new Long("5"));
	        Interest interest6 = interestDao.get(new Long("6"));
	        
	        HashSet<Interest> interests1 = new HashSet<Interest>();
	        interests1.add(interest1);
	        interests1.add(interest2);
	        interests1.add(interest3);
	        HashSet<Interest> interests2 = new HashSet<Interest>();
	        interests2.add(interest4);
	        interests2.add(interest5);
	        interests2.add(interest6);
	        
	        
	        
	        // Person-----------------------------------------------
	        System.out.println("\nPerson Tests---------------------------------\n");
	        // test getId
	        
	        System.out.println(personDao.get(new Long("1")));

	        // test savePerson with invalid id
	        Person badId = new Person(new Long("0"), "fred", "sinatra", 50, location1, interests1);
	        
	        try {
				personDao.savePerson(badId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Successfully caught Bad Person Id");
			}
	        
	        // test update using savePerson with valid Id
	        try {
	        	Person goodId = new Person(new Long("1"), "fred", "sinatra", 5, location2, interests2);
				personDao.savePerson(goodId);
				System.out.println("Updated person = " + personDao.get(new Long("1")));
				Person changeBack = new Person(new Long("1"), "Jeff", "Sinatra", 20, location1, interests1);
				personDao.savePerson(changeBack);
				System.out.println("Updated person changed back to: " + personDao.get(new Long("1")));
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        // test add a person using savePerson
	        
	        try {
	        	Person newPerson = new Person("Add", "ATest", 40, location1, interests1);
	        	newPerson = personDao.savePerson(newPerson);
	        	System.out.println("Testing add new person-----------");
	        	System.out.println("The index of the new person is: " + newPerson.getId());
	        	System.out.println("The added person is: " + personDao.get(newPerson.getId()));
	        	personDao.removePerson(newPerson);
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        // test findInterestGroup---------------------------------
	        System.out.println("\nTesting Find Interest Group-----------------------------\n");
	        Interest currentInt = new Interest(new Long("3"), "Skydiving");
	        Location currentLoc = locationDao.get(new Long("1"));
	        Person person1 = personDao.get(new Long("1"));
	        Person person2 = personDao.get(new Long("2"));
	        
	        System.out.println("Testing findInterestGroup-----------");
	        Set<Person> ILgroup = personDao.findInterestGroup(currentInt, currentLoc);
	        if (ILgroup.size() == 2){
	        	System.out.println("Interest group correctly has 2 members");
	        }
	        if (ILgroup.contains(person1)){
	        	System.out.println("Interest group correctly has " + person1.getFirstname() + " " + person1.getLastName());
	        }
	        if (ILgroup.contains(person2)){
	        	System.out.println("Interest group correctly has " + person2.getFirstname() + " " + person2.getLastName());
	        }

	        
	        // Location-----------------------------------------------------
	        
	        System.out.println("\nLocation Tests-------------------------------------\n");
	        
	        // test Location get
	        
	        System.out.println("Test getting a location from a Person");
	        Person person10 = personDao.get(new Long("10"));
	        Location loc = locationDao.get(person10);
	        Location testLoc = new Location(new Long("4"), "Muddy Flats", "MN", "USA");
	        System.out.println("Location get method works: " + loc.equals(testLoc));
	        System.out.println(loc);

	        // test saveLocation with invalid id
	        Location badIdLocation = new Location(new Long("0"), "anywhere", "anyState", "anyCountry");
	        
	        try {
				locationDao.saveLocation(badIdLocation);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Successfully caught Bad Location Id");
			}
	        
	        // test update using saveLocation with valid Id
	        try {
	        	Location goodIdLocation = new Location(new Long("1"), "London", "England", "UK");
				locationDao.saveLocation(goodIdLocation);
				System.out.println("Updated location = " + locationDao.get(new Long("1")));
				Location changeBack = new Location(new Long("1"), "New Orleans", "LA", "USA");
				locationDao.saveLocation(changeBack);
				System.out.println("Updated location changed back to: " + locationDao.get(new Long("1")));
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        // test add a location using saveLocation
	        
	        try {
	        	Location newLocation = new Location("Metairie", "LA", "USA");
	        	newLocation = locationDao.saveLocation(newLocation);
	        	System.out.println("Testing add new location-----------");
	        	System.out.println("The index of the new location is: " + newLocation.getId());
	        	locationDao.removeLocation(newLocation);
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // Interest-----------------------------------------------------
	        
	        System.out.println("\nInterest Tests--------------------------------\n");
	        
	        // test interest get
	        Person testPerson = personDao.get(new Long("1"));
	        Set<Interest> intSet = interestDao.get(testPerson);
	        if (intSet.size() == 3)
	        	System.out.println("Interest set size correctly = " + intSet.size());
	        Interest int1, int2, int3;
	        int1 = new Interest(new Long("1"), "Drama");
	        int2 = new Interest(new Long("2"), "Underwater Basket Weaving");
	        int3 = new Interest(new Long("3"), "Skydiving");
	        if (intSet.contains(int1) && intSet.contains(int2) && intSet.contains(int3))
	        	System.out.println("Interest set returns the 3 correct entries");
	        
	        
	        
	        // test saveInterest with invalid id
	        Interest badIdInterest = new Interest(new Long("0"), "AnyInterest");
	        
	        try {
				interestDao.saveInterest(badIdInterest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Successfully caught Bad Interest Id");
			}
	        // test update using saveInterest with valid Id
	        try {
	        	Interest goodIdInterest = new Interest(new Long("1"), "Comedy");
				interestDao.saveInterest(goodIdInterest);
				System.out.println("Updated interest = " + interestDao.get(new Long("1")));
				Interest changeBack = new Interest(new Long("1"), "Drama");
				interestDao.saveInterest(changeBack);
				System.out.println("Updated interest changed back to: " + interestDao.get(new Long("1")));
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        // test add a interest using saveInterest
	        
	        try {
	        	Interest newInterest = new Interest("Spaceflight");
	        	newInterest = interestDao.saveInterest(newInterest);
	        	System.out.println("Testing add new interest-----------");
	        	System.out.println("The index of the new interest is: " + newInterest.getId());
	        	interestDao.removeInterest(newInterest);
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
