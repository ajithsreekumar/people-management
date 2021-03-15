# people-management
App to manage people and their address

### Build and Run the application
Go to the repo and buld the mvn project.
`mvn clean install`
Now go into the target folder and run the jar file generated.
`java -jar people-management-0.0.1-SNAPSHOT.jar`
This will run the Spring Boot application starting up a  server on localhost:8080.

### Operation and API details
1. Add Person: Add a new Person
Sample request: `curl --location --request POST 'http://localhost:8080/demo/person' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Ajith",
    "lastName": "Sreekumar"
}'`
`curl --location --request POST 'http://localhost:8080/demo/person' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Steve",
    "lastName": "Jobs"
}'`
2. Edit Person: Edit an existing Person
Sample request: `curl --location --request PUT 'http://localhost:8080/demo/person' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 1,
    "firstName": "Aji",
    "lastName": "Sree"
}'`
3. Delete Person: Delete an existing Person
Sample request: `curl --location --request DELETE 'http://localhost:8080/demo/person/2'`
4. Add Address to Person: Add address to an existing Person
Sample request: `curl --location --request POST 'http://localhost:8080/demo/person/1/address' \
--header 'Content-Type: application/json' \
--data-raw '{
    "street": "s1",
    "city": "c1",
    "state": "st1",
    "postalCode": "p1"
}'`
5. Edit an existing Address: Edit an existing address for a person
Sample request: `curl --location --request PUT 'http://localhost:8080/demo/person/1/address' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 1,
    "street": "s2",
    "city": "c2",
    "state": "st2",
    "postalCode": "p2"
}'`
6. Delete an existing Address: Delete an existing address
Sample request: `curl --location --request DELETE 'http://localhost:8080/demo/address/1'`
7. List People: List all the existing people
Sample request: `curl --location --request GET 'http://localhost:8080/demo/person/list'`
8. Count number of People: Get the total count of people available.
Sample request: `curl --location --request GET 'http://localhost:8080/demo/person/count'`

### Notes
* An in-memory database called H2 is used as the database for the application.
* It is assumed that each person can have multiple addresses
* An id is generated for a person and address during creation, and during updates this id is used.

