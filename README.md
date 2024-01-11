# Retail-Prices

## Exercise
In this project we have created a rest endpoint that given a brand id, product id and a date, returns the price applies 
for those values, using the higher priority in case of matching more than one.

The example DB where we are going to retrieve the data is the next one.

| BRAND_ID | START_DATE | END_DATE | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----------|------------|----------|------------|------------|----------|-------|------|
| 1 | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 1 | 35455 | 0 | 35.50 | EUR |
| 1 | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 2 | 35455 | 1 | 25.45 | EUR |
| 1 | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 3 | 35455 | 1 | 30.50 | EUR |
| 1 | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 4 | 35455 | 1 | 38.95 | EUR |

## Explanation

To structure the implementation following an hexagonal architecture I have divided the code 
in 3 folders: domain, application and infrastructure.

### Domain Layer
The core logic and domain entities of the service are implemented in the Domain layer, where I have set the entity that 
we are going to need (Price), the implementation of the logic itself (PriceServiceImpl), which will be exposed to any 
entry point trough its interface (PriceService) and the interface of the repository we need (PriceRepository), which 
will need to be implemented in the infrastructure layer.

### Application Layer
The entry point will be a REST API, implemented in the Application Layer. There we will have 2 entities to represent the
request and response we will have in the endpoint (PriceRequest and PriceResponse) and the endpoint itself will be 
implemented in PriceController, where we will call to the PriceService (implemented in the domain layer) and as a last 
step we need to map (using the PriceMapper) the Price returned by the domain service to the PriceResponse.

### Infrastructure Layer
The repository will be implemented in the Infrastructure Layer, in our case, an embedded H2 database. For that we need 
to implement the interface PriceRepository defined in the domain layer (PriceRepositoryImpl). We will define the call to
the DB in SpringDataPriceRepository, which will implement a Query to the DB using the entities BrandEntity and 
PriceEntity. Then, in PriceRepositoryImpl we simply need to call to this new repository and map (using the 
PriceEntityMapper) the List of PriceEntity that returns, to the object that our domain understand, a List of Price.

### Testing
Each class have its own unit tests to confirm its behavior is correct, and in PricesApplicationTests we have an 
integration test with all the cases required in the exercise.

To test them, we simply need to install [Java](https://www.oracle.com/es/java/technologies/downloads/#java17) and [Maven]
(https://maven.apache.org/download.cgi), go to the project directory and execute 
```
mvn verify
```

To run the project we can execute 

```
mvn spring-boot:run
```
The documentation of the API is in the file [api-docs.yaml](src/main/resources/RetailPricesApi.yaml).
If we want to see the docs in Swagger, after running the project we can go to http://localhost:8080/swagger-ui/index.html

To test the endpoint, after running the project, we can use [Postman](https://www.postman.com/downloads/) to create a 
new request to the endpoint. We can import the next curl.
```
curl --location 'http://localhost:8080/price?brandId=1&productId=35455&appDate=2020-12-30T23%3A59%3A59.000Z'
```
![Postman Screenshot](https://github.com/Aslat/retail-prices/blob/2d25da71af101c66c08cc80abd4db074c1d11b32/Captura%20de%20pantalla%202024-01-11%20130206.png)