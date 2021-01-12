## Warehouse Software

The software holds:
* articles which are loaded from inventory.json `src/main/resources/static/inventory.json`
* products which are loaded from products.json `src/main/resources/static/products.json`

### How to run code locally

#### Build and start Docker container:

`docker build -t inventory_app . `

`docker run -d -p 8080:8080 -t inventory_app`

#### Interact with the software:

* Get all products and quantity of each that is an available with the current inventory:

`curl http://localhost:8080/available-products`

* Remove(Sell) a product and update the inventory accordingly. Only one product type allowed per purchase request:

`curl -X POST -H "Content-Type: application/json" -d '{"productName": "Dinning Table", "quantity": 1}' http://localhost:8080/purchase-product`

#### Stop Docker container:

`docker ps` - to find container id

`docker stop container_id`
