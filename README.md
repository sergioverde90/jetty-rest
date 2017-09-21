Because this project is intended to be used with a standard web
profile server (like Tomcat or Jetty) no external modules will be loaded, therefore there are no dependency injection 
framework (like Spring, EJB or CDI).

This project uses **constructor dependency injection**. Constructor injection is suitable for testing. The used approach 
to inject dependencies is based on **@PostConstruct** web standard. 

## BUILD REQUISITES
* JDK +8
* Maven +3.3

## BENEFITS
* +90% test coverage
* Thin war
* Immutable entities

## USED PATTERNS
* Decorator
* Command *(in lambda java 8 style)*
* Strategy
* Facade pattern between services
* Factory method
* Builder pattern that allow public fields *(avoid getter / setter calls)*
* Entity-Control-Boundary *as object model*

## USED PRINCIPLES
* Interface Segregation
* Open Close Principle
* Don't Repeat Yourself
* Single Responsibility Principle
* Dependency Injection

## HOW TO RUN
```bash
mvn jetty:run
```

## HOW TO RUN WITH DOCKER
```bash
docker build -t jetty .
```
```bash
docker run -it --name jetty -p 8080:8080 jetty
```

## HOW TO TEST
### matched entries
```bash
curl -XPOST -H "Content-Type: application/json" -d {\"source\":\"192.168.1.5\",\"destination\":\"198.168.1.5\",\"protocol\":\"tcp/any\"} http://localhost:8080/app/resources/intelliment/acl
``` 
### entire ACL
```bash
curl -i http://localhost:8080/app/resources/intelliment/acl
```
### specific ACL entry
```bash
curl -i http://localhost:8080/app/resources/intelliment/acl/1
```  