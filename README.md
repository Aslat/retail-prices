# Retail-Prices

En la base de datos de comercio electrónico de la compañía disponemos de la tabla
PRICES que refleja el precio final (pvp) y la tarifa que aplica a un producto de una cadena
entre unas fechas determinadas. A continuación, se muestra un ejemplo de la tabla con los
campos relevantes:

PRICES
-------

| BRAND_ID | START_DATE | END_DATE | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----------|------------|----------|------------|------------|----------|-------|------|
| 1 | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 1 | 35455 | 0 | 35.50 | EUR |
| 1 | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 2 | 35455 | 1 | 25.45 | EUR |
| 1 | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 3 | 35455 | 1 | 30.50 | EUR |
| 1 | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 4 | 35455 | 1 | 38.95 | EUR |


Campos:

* BRAND_ID: foreign key de la cadena del grupo.
* START_DATE , END_DATE: rango de fechas en el que aplica el precio tarifa indicado.
* PRICE_LIST: Identificador de la tarifa de precios aplicable.
* PRODUCT_ID: Identificador código de producto.
* PRIORITY: Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rago de
fechas se aplica la de mayor prioridad (mayor valor numérico).
* PRICE: precio final de venta.
* CURR: iso de la moneda.

Se pide:
* Construir una aplicación/servicio en SpringBoot que provea una end point rest de
consulta tal que:
  * Acepte como parámetros de entrada: fecha de aplicación, identificador de
producto, identificador de cadena.
  * Devuelva como datos de salida: identificador de producto, identificador de
  cadena, tarifa a aplicar, fechas de aplicación y precio final a aplicar.
  
Se debe utilizar una base de datos en memoria (tipo h2) e inicializar con los datos del
ejemplo, (se pueden cambiar el nombre de los campos y añadir otros nuevos si se quiere,
elegir el tipo de dato que se considere adecuado para los mismos).

* Desarrollar unos test al endpoint rest que validen las siguientes peticiones al servicio
con los datos del ejemplo:

  * Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1
  * Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1
  * Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1
  * Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1
  * Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1

Se valorará:
* Diseño y construcción del servicio.
* Calidad de Código.
* Resultados correctos en los test.

# Explanation

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
And then we can use [Postman](https://www.postman.com/downloads/) to create a new request to the endpoint
![Postman Screenshot](https://github.com/Aslat/retail-prices/blob/main/Captura%20de%20pantalla%202024-01-08%20163545.png)
