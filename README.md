This project uses constructor dependency injection. Because this project is intended to be used with a standard web 
profile server (like Tomcat or Jetty) no external modules will be loaded, therefore there are no dependency injection 
framework like Spring, EJB or CDI.

The used approach to inject dependencies is based on @PostConstruct web standard.

## BUILD REQUISITES
* JDK 8+
* Docker 17.03 CE+
* Maven 3+

## USED PATTERNS
* Decorator
* Command *(in lambda java 8 style)*
* Strategy
* Factory method
* Builder

## USED PRINCIPLES
* Interface Segregation
* Open Close Principle
* Don't Repeat Yourself
* Single Responsibility Principle
* Dependency Injection