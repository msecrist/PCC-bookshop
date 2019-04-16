# Notes on Bookshop Web application

## If running locally

### Set up local MySQL database
Install MySql (I used version 8.0.13). 
You can use MySql Workbench to manage the database.
Create a `bookshop` schema.

### Set up local GemFire cluster
The local gemfire cluster (currently running 9.6.0) is configured to have one region (Customer) and no security.
The system can be started with the following commands.

```bash
start locator --name=locator
start server --server-port=0 --locators=localhost[10334] --name=server1
start server --server-port=0 --locators=localhost[10334] --name=server2
start server --server-port=0 --locators=localhost[10334] --name=server3
```

The first time the cluster is started, be sure to create the customer and books regions.

```bash
create region --name=Customer --type=PARTITION
create region --name=Books --type=REPLICATE
```

### Testing the application
Once the database and GemFire cluster have been started, you should be able to start the application.
A few tests can verify that all is working.

```bash
curl localhost:8080/book/101
curl localhost:8080/customer/1001
```


### Setup application
When running the application locally, the local mysql database and gemfire cluster are configured in the `application-local.properties` file.
Be sure to engage the local profile. For IntelliJ, that would be `Run-> Edit Configurations` and then set in the `VM Arguments` field.

```bash
-Dspring.profiles.active=local
```

## If running on a PCF Instance
The setup & deployment to PCF is really quite straightforward.

### Set up MySql instance
You'll need a MySql service instance called `bookshop-db` that will be populated with all the books & customers (1000 entries each)

### Set up a PCC instance
You'll need to create a PCC instance. Size isn't terribly important, but I've been using a cluster with 4 servers.

Service name should be called `bookshop-cache`.

You'll need to create two regions. 

1. Create a region called `Customer` of type `PARTITION`
1. Create a region called `Books` of type `REPLICATE`

### Deploying the app
Once the services are created, you should be able to just package and push the app to your PCF instance.
There is a manifest that specifies the two services above that will be bound to the app.
You may need to modify the name of the application if there is a conflict in route name.

### Testing the application
Once the application has successfully deployed, a few tests can verify that all is working.
Make note of the route for the deployed application.

```bash
curl <route for pcc app>/book/101
curl <route for pcc app>/customer/1001
```
