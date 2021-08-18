
# Reading Is Good

ReadingIsGood is an online books retail firm which operates only on the Internet. Main
target of ReadingIsGood is to deliver books from its one centralized warehouse to their
customers within the same day. That is why stock consistency is the first priority for their
vision operations.

## Project Infrastructure

The technologies I use are as follows.

```
Programming lang.   : Java 11
Framework           : Spring boot 2.5.3
Databases           : H2
Databases Version C.: Flyway
Build Management    : Maven, Docker
Communication       : Rest
Internationalization(i18n) : EN,TR 
```

The i18n operation is performed on the error messages.

## How to run   

Default port: 9090 (changeable with docker-compose)

1-) Docker Image for prod <br>
You can run it with docker-compose as I have published the docker image to docker hub. <br>
`docker-compose -f docker-compose-prod.yml up -d`

2-) Maven and Docker Image for local <br>
First we need to package then building the docker image.We can run it with docker-compose. <br>
`mvn clean package` <br>
`docker build -t reading-is-good-enes .` <br>
`docker-compose -f docker-compose-local.yml up -d`

## How to use

There are two types of roles in the system. These are ADMIN and CUSTOMER.<br>
**Account Informations**
```
            Admin      Customer
username:   admin       user
pasword :   admin       user
```

*Admin Permissions*
- Create Customer
- Create Book
- Update Book Stock
- Cancel Order
- Get Order Details
- Get Orders By Date Interval

*Customer Permissions*
- Get Customer Orders
- Create Order
- Cancel Order (only own orders)
- Get Order Details
- Get Monthly Statistics

**Dummy data was available in the system for convenience while testing the system. If you don't want to use dummy data, you can create a new customer and proceed over it.**
- There are three books in the system.
- 'user' has 4 orders.

## Swagger and Postman
Swagger URL:`http://localhost:9090/swagger-ui.html` <br> 
Postman collection: `Reading is good.postman_collection.json`

