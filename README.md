# webflux-test

1. Run test server with dummy fix
```
cd webflux
mvn -DturnOnFastMode=true spring-boot:run
```
or without fix
```
cd webflux
mvn spring-boot:run
``` 
2. Run gatling test (results in $PROJECT_HOME/gatling/target/gatling dir)
```
cd gatling
mvn gatling:execute
``` 