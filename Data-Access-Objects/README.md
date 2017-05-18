# Data Access Objects

#### Description
You will use JDBC to create a series of Data Access Objects that will allow for operations on the schema created in the [Schema Design Assignment](https://github.com/mborencooksys/Schema-Design).

#### Duration
3 hours

#### Skills
Queries, The JDBC API, interacting with database structures through core Java, Data Access Object design, properly managing connections to database resources

## Concept
The concept of this assignment is to create a series of *Data Access Objects* (**DAOs**) that will allow for clean, easy interaction with the database. A test suite will be created to demonstrate correct functionality of the DAOs.

## Instructions

* Create POJOs (**Person**, **Location**, **Interest**) to represent the tables in the database schema.
  * Person
    * id - `Long`
    * firstName - `String`
    * lastName - `String`
	* age - `Integer`
  * Location
    * id - `Long`
    * city - `String`	
    * state - `String`
	* country - `String`
  * Interest
    * id - `Long`
	* title - `String`
	
* Create a **PersonDao** class. Implement the following *public* methods in the **PersonDao** class. **Note:** You may implement any number of additional *private* methods to support the following required *public* methods.
  * Implement a method `get(id)`, where `id` is the primary key of the Person you want to retrieve
  * Implement the `get(id)` method so that it will return a **Person** that accurately represents the row in the Person table that has a *Primary Key* equal to the value provided as `id` in the parameters of the `get` method
  * Implement a `save(Person)` method such that 
    1. The data in the `Person` parameter is saved as a new entry in the database if the *Id* field is null
    2. The data in the `Person` parameter is used to update an existing entry in the database if the Id field is not null and already exists in the database
    3. An exception is thrown if the Id field is not null and does not already exist in the database
  * Implement a `findInterestGroup(Interest, Location)` method that 
    1. Executes the *Stored Procedure* defined in the [Schema Design Assignment](https://github.com/mborencooksys/Schema-Design)
    2. Transforms the ResultSet obtained from executing the *Stored Procedure* into a `Set<Person>`
    3. Returns the `Set<Person>`
 
* Create the **LocationDao** and **InterestDao** classes
  * Implement `get(id)` for both classes
  * Implement `save(Location)` and `save(Interest)`

* Add a *"location"* field to the **Person** class that is the data type `Location`

* Add a *"interests"* field to the **Person** class that is the data type `Set<Interest>`

* Modify the `get(id)` method in the **PersonDao** to interact with the **LocationDao** and **InterestDao** in order to ensure that all **Person** objects retrtieved have accurate data in their  *interests* and *location* fields.

* Modify the `save(Person)` method in the **PersonDao** to also update the *Location Table*, the *Interest Table*, and any related *Join Tables* or *Join Columns* so that the contents of the saved **Person** object are stored accurately

* Add Test Cases to demonstrate that
  * A new **Person** object can be stored
  * A **Person** object can be retrieved
  * An existing **Person** can be updated
  * A new **Location** can be stored
  * A **Location** object can be retrieved
  * An existing **Location** can be updated
  * A relationship between a **Person** and a **Location** can be stored
  * A new **Interest** can be stored
  * An **Interest** object can be retrieved
  * An exiting **Interest** can be updated
  * A relationship between a **Person** and a series of **Interests** can be stored
  * The `PersonDao.findInterestGroup(Interest, Location)` method operates with accurate results