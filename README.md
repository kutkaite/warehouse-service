# Warehouse Service

Warehouse service is the functionality related to the product and article inventory operations. 
The API allows you to integrate for getting all available product inventory and to make sure the
inventory is update when products are sold.

The software holds:
* articles which are loaded from inventory.json `src/main/resources/static/inventory.json`
* products which are loaded from products.json `src/main/resources/static/products.json`

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Java-based framework
* [Kotlin](https://kotlinlang.org/) - Programming language
* [Maven](https://maven.apache.org/) - Dependency Management
* [H2](https://h2database.com/) - Java SQL database

## How to run code locally

### Prerequisite

Follow the instructions [here](https://docs.docker.com/get-docker/) to install docker. 
If you have `Homebrew` installed, simply run 
```
brew install --cask docker
``` 

### Build and start Docker container:

Build a jar file and an image `warehouse_service`
```
docker build -t warehouse_service . 
```

Start the container with the newly built image `warehouse_service`

```
docker run -d -p 8080:8080 -t warehouse_service
```

### Interact with the software:

* Get all products and quantity of each that is an available with the current inventory:

```
curl http://localhost:8080/available-products | json_pp -json_opt pretty
```

* Remove(Sell) a product and update the inventory accordingly. Only one product type allowed per purchase request:

```
curl -X POST -H "Content-Type: application/json" -d '{"productName": "Dinning Table", "quantity": 1}' http://localhost:8080/purchase-product | json_pp -json_opt pretty
```

### Run tests:

- Find container name
```
docker ps
```

- Get inside the container
```
docker exec -it name /bin/sh
```

- Run tests

```
mvn test
```

- Exit the container

```
exit
```

### Stop Docker container:

Find container id

```
docker ps
```

Stop the container

```
docker stop container_id
```
