## Warehouse Software

The software holds:
* articles which are loaded from inventory.json `src/main/resources/static/inventory.json`
* products which are loaded from products.json `src/main/resources/static/products.json`

### How to run code locally

#### Build and start Docker container:

Build a jar file and an image `warehouse_app`
```
docker build -t warehouse_app . 
```

Start the container with the newly built image `warehouse_app`

```
docker run -d -p 8080:8080 -t warehouse_app
```

#### Interact with the software:

* Get all products and quantity of each that is an available with the current inventory:

```
curl http://localhost:8080/available-products | json_pp -json_opt pretty
```

* Remove(Sell) a product and update the inventory accordingly. Only one product type allowed per purchase request:

```
curl -X POST -H "Content-Type: application/json" -d '{"productName": "Dinning Table", "quantity": 1}' http://localhost:8080/purchase-product | json_pp -json_opt pretty
```

#### Run tests:

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

#### Stop Docker container:

Find container id

```
docker ps
```

Stop the container

```
docker stop container_id
```
