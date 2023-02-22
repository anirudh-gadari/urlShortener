## URL Shortener Project Readme file

Description:

A Java Spring Boot project that takes longUrl and shortens it into shortUrl. This application has majorly two flows.

1) generate shortUrl: (Refer UrlShortener_FlowDiagram1.png for flow diagram)

   when user submits the form, it will shorten into shortUrl with length between 1-8 characters, saves it in the database and returns shortUrl to the user.

   If the user submits the previously shortened longUrl again, its previously generated shortUrl is fetched from the H2 Database.

2) redirect to longUrl when shortUrl is entered: (Refer UrlShortener_FlowDiagram2.png for flow diagram)

   when user hits the shortened Url in the web application, it will redirect to its corresponding longUrl if exists or throws InvalidUrlException.


----------------------------------------------------------------------------------------------------------------------------------------------------------

this application runs at 8080 port.

Homepage Url: http://localhost:8080

H2 Console Url: http://localhost:8080/h2-console

Swagger Url: http://localhost:8080/swagger-ui.html

----------------------------------------------------------------------------------------------------------------------------------------------------------
Metrics:

This application generates shortUrl ranging from 1 to 8 characters in length and each character
can be any of 64 possible characters(A-Z,a-z,0-9, - and \_)

total number of unique shortUrls we can generate
= 64^1+64^2+64^3....+64^8 = 281.47 trillion combinations

Database used: H2 Database

----------------------------------------------------------------------------------------------------------------------------------------------------------
Steps to run the application:

To clean:

windows users - mvn clean
mac users - ./mvnw clean

To create jar file for this application:

windows users - mvn install
mac users - ./mvnw install

note: jar file is saved in the target sub-directory

To test unit test cases:

windows users - mvn clean test
mac users - ./mvnw clean test

To run:

we can run this application in two ways from command prompt.

1. using Jar:

   first go the project directory in command line or terminal
   
   next create jar file as mentioned above commands
   
   now go to the target sub directory as jar was created inside of it using below command
   
   cd target
   
   java -jar urlShortener-0.0.1-SNAPSHOT.jar
   
   
2. using below maven command:  

   mvn spring-boot:run (windows)
   ./mvnw spring-boot:run (mac users)

press Cntrl + C to stop the execution
