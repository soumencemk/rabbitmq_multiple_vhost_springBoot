# RabbitMQ multiple virtual host spring boot

## Configuration on Rabbit

Following virtual hosts --

![Virtual Hosts](./images/vHosts.png)

Following users --

![Users](./images/users.png)

And following queue config --

![Queues](./images/queues.png)

## On Spring boot
Setting up a `SimpleRoutingConnectionFactory` as following --

```java
@Bean
    public ConnectionFactory connectionFactory() {
        SimpleRoutingConnectionFactory routingConnectionFactory = new SimpleRoutingConnectionFactory();
        Map<Object, ConnectionFactory> targetConnectionFactories = new HashMap<>();
        targetConnectionFactories.put("dmm_vhost", dmm_vhost_ConnectionFactory());
        targetConnectionFactories.put("pga_vhost", pga_vhost_ConnectionFactory());
        routingConnectionFactory.setTargetConnectionFactories(targetConnectionFactories);
        return routingConnectionFactory;
    }
```

the targetConnectionFactories has to define all the other v-host connections :-

```java
private ConnectionFactory dmm_vhost_ConnectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setVirtualHost("dmm_vhost");
        connectionFactory.setUsername("dmmRabbitUser");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    private ConnectionFactory pga_vhost_ConnectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setVirtualHost("pga_vhost");
        connectionFactory.setUsername("pgaRabbitUser");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    } 
```
Now, while publishing, we need to mention which virtual host we want to publish to --

```java
SimpleResourceHolder.bind(rabbitTemplate.getConnectionFactory(), "<VHOST_NAME>");
rabbitTemplate.convertAndSend("<EXCHANGE>", "<ROUTING_KEY>", "<PAYLOAD>");
```
### How to run this project

```sh
./mvnw spring-boot:run
```



