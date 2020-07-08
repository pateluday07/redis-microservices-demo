# redis-microservices-demo
This project is to demonstrate how we can implement **Redis** 
in a microservice architecture. So here I have implemented 
**Redis** in a **Spring Boot** service, and here data will be 
stored in the **Redis cache** if it's pass certain criteria, 
and when users ask for that particular data we will return it 
from the cache instead of taking that data from the database, 
as well as we will update and delete data from the cache.

We have here **Zuul API Gateway** which is the **proxy** for our 
microservice architecture, and it takes care of the **load balancing** 
and forward requests to the particular service. 

We have here **Eureka Server** which registers the **Services** and 
**Zuul API Gateway**. 

This is a high-level overview of what I have built, and the detailed 
information for all the components available below.

### Requirements To Run Applications
* JDK 8
* MySQL
* Redis
* IDE

### Dependencies And Tools Used To Build Applications
* Git
* JDK 8
* Gradle
* Maven
* Spring Boot Web
* Spring Boot Data JPA
* MySQL
* Redis
* Lombok
* IDE
* Eureka Client
* Eureka Server
* Zuul Proxy
* Hystrix

### Eureka Server
Eureka Server is an application that holds information about all 
client-service applications. 

Every Microservice will register into the Eureka server and the 
Eureka server knows all the client applications running on each 
port and IP address. 

Eureka Server is also known as Discovery Server.

##### To Run "eureka-server"
1. Go to the `/redis-microservices-demo/eureka-server` directory and 
open terminal

2. Execute the following command in the terminal

       gradlew bootrun
   
   or go to the `/redis-microservices-demo/eureka-server/src/main/java/com/eurekaserver` directory and run the **EurekaServerApplication** class.
       
And now we can access the **Eureka Server** on this link: 
[Eureka Server] (http://localhost:8761/)

You will see here registered **Services** and **Zuul API Gateway** 
once we start them.

### redis-spring-boot-service
This is a spring boot service and I have implemented Redis here for catching, and initially, all the data are stored in the **MySQL**. So here I built this service similar to **Instagram** here we have users and their followers, so I put a condition to store users in the cache and the condition is if the **followers > 12,000** then the users will be stored in the cache. 

So when you get the user by id using **GET By Id** API and if the user has passed the condition, then it will be stored in the cache and when you next time hit this API the user will be returned from the cache, and you won't see any logs in your console because we are not getting it from the database.

Similarly when you update the user followers using **PUT** API for example if you set user's **followers < 12,000**, then that user will be removed from the cache and when you try to get that user it will return from the database and you will see logs in the console, and if you update user **followers > 12,000** so it will be added in the cache and then when you try to get that user it will be returned from the cache.

And when you delete a user using **DELETE** API, and if the user is available in the cache then it will be removed from the cache and database, and then you will try to get that user you will get **NOT FOUND (404)** because that user is not available neither in cache nor in database.

Additionally, we have two more APIs **POST** to add new users and **GET All** to get all the users. 

##### To Run "redis-springboot-demo" Service
1. Go to the `/redis-microservices-demo/redis-springboot-demo` directory and 
open terminal

2. Execute the following command in the terminal

       mvnw spring-boot:run
   
   or go to the `/redis-microservices-demo/redis-springboot-demo/src/main/java/com/redis/springbootdemo` directory and run the **RedisSpringBootDemoApplication** class.

You can run the multiple instances of this service and access them using **ZUUL Gateway**, here is the collection of APIs import them in your **POSTMAN** and play with the APIs.

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/bbad50e304d2723139fc)

**NOTE: make sure ZUUL Gateway is up and running before you access APIs**
        
### Zuul API Gateway
Zuul Server is an API Gateway application. It handles all the 
requests and performs the dynamic routing of microservice 
applications. It works as a front door for all the requests. It is 
also known as Edge Server.

Zuul is built to enable dynamic routing, monitoring, resiliency, and
security. Here we are covering dynamic routing and resiliency.

We have implemented Hystrix to make our gateway resilient, so we 
have a fallback mechanism here, in case the services are not 
available, so the user can get a nice readable message instead of 
nasty errors.

##### To Start "zuul-gateway-service"
1. Go to the `/redis-microservices-demo/zuul-gateway-service` directory and 
open terminal

2. Execute the following command in the terminal

       gradlew bootrun
       
   or go to the `/redis-microservices-demo/zuul-gateway-service/src/main/java/com/zuulgateway` directory and run the **ZuulGatewayServiceApplication** class.

And now we can access the services through this gateway. I request 
you to start multiple instances of the **redis-springboot-demo**. So you can see that the requests are 
forwarding to the multiple instances to balance the load.

<p style="align-content: center">
  <b>Thank You</b>
</p>