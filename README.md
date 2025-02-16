Step by step learning of Spring Boot integration with Keycloak IAM container will be covered in this repository.

Each learning step will be given a different tag number, and the same would be updated in this README.

# Step 1 - Introduction to Spring Security
This is a gradle based project, using `org.springframework.boot:spring-boot-starter-web` and `org.springframework.boot:spring-boot-starter-security`.

See the default port for accessing the web services in `application.yaml`.

# Step 2 - Security filter chain
Added a very basic security filter chain to authenticate the user based on the URL. See `SecurityConfiguration` for in-memory user details set up as an example.

URI `/bye` doesn't need authentication, while its applied for `/hello`.

# Step 3 - Keycloak integration
There are several mini-steps to get this working. Details would be added based on issues and queries raised by other developers interested in this project.

You can also see [keycloak-quickstarts](https://github.com/keycloak/keycloak-quickstarts) on github as an alternative.

## Setting up Keycloak
A `grant_type` of password will be used. High level view of the sub-steps are captured below:

* A new realm `open-banking-realm` is defined
* A client `open-banking` is defined, with generated credentials locally copied for later usage
* One or more users are defined. Don't forget to add name and email address fields, along with username.
* Define the user password.
* Don't forget to check "Email verified" for the user, and remove all the "Required user actions" from the list.

## Dependency changes
The code in this project uses the following libraries:

* `org.springframework.boot:spring-boot-starter-security`
* `org.springframework.boot:spring-boot-starter-oauth2-resource-server`

## Define appliation properies
The `appliction.yaml` flie only needs to provide JWT URL, which will include the realm created above.

## Testing the REST API security
Use curl to POST and HTTP request to obtain an access_token (MS Windows command line example):

```sh
curl -X POST "http://localhost:9001/realms/open-banking-realm/protocol/openid-connect/token" ^
  -H "Content-Type: application/x-www-form-urlencoded" ^
  -d "client_id=open-banking" ^
  -d "client_secret=BxfDdnv47dGz3p9kzRenMx5NmqcYkfNi" ^
  -d "grant_type=password" ^
  -d "username=ragamuffin" ^
  -d "password=ragamuffin"
```

Extract the value of access_token as an enviornment variable from the response, and use it to invoke the URL:

```sh
curl http://localhost:8081/hello -H "Authorization: Bearer "%access_token%
```
