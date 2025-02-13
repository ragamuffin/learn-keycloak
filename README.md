Step by step learning of Spring Boot integration with Keycloak IAM container will be covered in this repository.

Each learning step will be given a different tag number, and the same would be updated in this README.

# Step 1 - Introduction to Spring Security
This is a gradle based project, using `org.springframework.boot:spring-boot-starter-web` and `org.springframework.boot:spring-boot-starter-security`.

See the default port for accessing the web services in `application.yaml`.

# Step 2 - Security filter chain
Added a very basic security filter chain to authenticate the user based on the URL.

URI `/bye` doesn't need authentication, while its applied for `/hello`.
