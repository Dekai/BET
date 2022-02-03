# Dekai backend template project
## Use this project as scaffold to speed up backend development
- clone the project
- Refactor the package and project name
- Create domain object
- Liquibase generate database tables
- TDD or Starts from controller -> service -> repository
- Test cases


### SpringBoot Application
- [X] Spring Data JPA - JpaRepository
    - [X] Relation tables One to Many and Many to one
    - [X] [Custom query](https://www.baeldung.com/spring-data-jpa-query)
    - [X] Intellij Plugin
        - [X] [Jpa Support](https://github.com/carter-ya/idea-plugin-jpa-support)
        - [X] [Jpa Buddy](https://dzone.com/articles/jpa-goes-even-easier-with-its-buddy)

- [X] Service
    - [X] DTO and mapper
    - [X] Transactional

- [X] Mapstruct
    - [Refer](https://auth0.com/blog/how-to-automatically-map-jpa-entities-into-dtos-in-spring-boot-using-mapstruct/)
    - [Sample code](https://github.com/Tonel/mapstruct-auth0)

- [X] Spring RestController
    - [X] Pageable - [pagination](https://www.baeldung.com/spring-data-jpa-pagination-sorting)
    - [X] Sort - http://localhost:8088/users?page=0&size=3&sort=id,desc
    - [X] Request and parameter - [validation](https://www.baeldung.com/spring-boot-bean-validation)
    - [X] Search - [Rest Specification](https://medium.com/quick-code/spring-boot-how-to-design-efficient-search-rest-api-c3a678b693a0)

- [X] Spring Exception/Error handler
    - [X] [Problem Libary](https://www.baeldung.com/problem-spring-web)
    - [X] [Sample code](https://github.com/Tonel/spring-boot-custom-error-handling)

- [X] Logging
    - [X] logback
    - [X] LoggingAspect
    - [X] logging pattern - Add userId into to the log
      ```xml
          <property name="CONSOLE_LOG_PATTERN" value="%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr([%X{USER_ID}|%X{URL}]){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}"/>
      ```
- [X] Interceptor - LoggerInterceptor

  [Refer](https://www.baeldung.com/spring-mvc-handlerinterceptor)

- [X] Swagger
    - [X] OpenAPI json file - http://localhost:8088/api-docs/ http://localhost:8088/api-docs.yaml
    - [X] Swagger UI - http://localhost:8088/swagger-ui

- [X] Database
    - [X] liquibase
        - [X] [How to use](https://www.baeldung.com/liquibase-refactor-schema-of-java-app)
        - [X] [Jpa Buddy](https://dzone.com/articles/jpa-goes-even-easier-with-its-buddy)
    - [X] H2

- [X] Spring security
    - [X] Role and Permission - TODO make it dynamic and configurable
    - [X] JWT - [Refer](https://www.toptal.com/spring/spring-security-tutorial)
    - [X] SecurityAdviceTrait - DkExceptionHandler

- [X] Test cases
    - [X] TDD
    - [X] Hamcrest

- [ ] comments

- [ ] i18n

- [ ] Readme.md

- [ ] git

- [ ] docker

### Exception handler
Reference: https://www.baeldung.com/exception-handling-for-rest-with-spring
- ExceptionHandler - Controller level, has to add it on each controler
- HandlerExceptionResolver - No control over the body of the response
- ControllerAdvice - Global handler
- ResponseStatusException - Create a util
- ErrorController and WhiteLabel Page

### CommandLineRunner
Spring Boot will run ALL CommandLineRunner beans once the application context is loaded
```java
@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(EmployeeRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
      log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
    };
  }
}
```
## Fun stuff
- Spring boot start Banner - http://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=Dekai's%20Demo

## TODO
- [ ] JH - Entity Audit listener
- [ ] Docker
- [ ] JH - PaginationUtil.generatePaginationHttpHeaders
- [ ] Metric log - [Refer](https://github.com/TutteRamson/MetricsLogger), Java source code in Qkr project
- [ ] OpenAPI - OAuth service


## Issue
- Mapstruct conflict with Lombok - [Solution](https://stackoverflow.com/questions/47676369/mapstruct-and-lombok-not-working-together)
- Spring circular reference - [Solution](https://stackoverflow.com/questions/36223752/prevent-cyclic-references-when-converting-with-mapstruct) [Solution1](https://springjourneydotblog.wordpress.com/2018/04/25/bidirectional-relation-between-entity-objects-with-mapstruct/) [Solution2]