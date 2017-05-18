# Intro To Controllers And Services

#### Description
In this assignment you will practice newly acquired knowledge of Spring to create a basic Controller and a supporting Service from the ground up. You will interact with the endpoints of the Controller through an HTML page that is generated based on the Swagger documentation of those endpoints.

#### Duration
2-3 hours

#### Skills
Spring fundamentals such as Inversion of Control, Dependency Injection, Spring Dependencies, Components, Controllers, & Services. REST and HTTP fundamentals such as Endpoints, URL Conventions, Path Variables, Path Parameters, HTTP Methods, HTTP Payload, JSON, HTTP Headers, HTTP Status Codes.

## Concept
The concept of this assignment is to create a Spring Boot API project that will keep track of People and their Friends.

## Instructions

* Use the [Spring Initializr](http://start.spring.io/) to create a Spring Boot project named *"Friendlr"*
* You will need to include at minimum the dependencies
    * Web
	* DevTools
* Add the [MapStruct Dependency](http://mapstruct.org/documentation/installation/) to the pom.xml
* Add the SpringFox and SpringFox-UI dependecies to the pom.xml
* Add the Docket bean to the Configuration class
* Create a POJO named *"Person"* with at least the fields
    * ID - `Long`
	* First Name - `String`
	* Last Name - `String`
	* Friends - `List<Person>`
* Create a *"PersonDto"* that replaces the `List<Person>` with a `List<Long>` that will contain IDs 
* Create the Mapper for Person
* Create a Controller named *"PersonController"* that controls the *"/person"* url
* Implement the following endpoints
  1. `GET /person`
    * This will retrieve a list of all PersonDtos
  2. `GET /person/{id}`
    * This will retrieve a single PersonDto as indicated by the {id}, or return the 404 - Not Found status code if that ID does not exist
  3. `POST /person`
    * This will accept JSON in the form of a PersonDto, create a Person instance, assign an ID to that Person, and store the Person instance. It will then return a 201 - Created status code. Return the 404 - Not Found status code if that ID does not exist
  4. `PUT /person/{id}`
    * This will overwrite the Person with the indicated {id} with the unmarshalled JSON contents of the body of this request or return the 404 - Not Found status code if that ID does not exist
  5. `DELETE /person/{id}`
    * This will delete the Person with the ID specified and remove all references to the specified ID in the Friends list of all other Person objects. Return the 404 - Not Found status code if that ID does not exist
* Create a *PersonService* and inject it into the *PersonController*. Implement methods to support the API.
* Ensure an error will be thrown and that an appropriate error message is returned to the user if in the Friends property of a JSON message body a user attempts to add reference the ID of a Person that does not exist
* Test the API using `localhost:8080/swagger-ui.html`