# springbootlearning
学习过程中的一些工程
1. async-springboot 异步处理
2. rabbitmq(生产者)和rabbitmqConsumer(消费者) 是spring boot 集成rabbitmq
3. rabbitmqAck 是加入了消息确认的机制，两种方式，一种是用RabbitTemplate的案例，另外一种是用原生的代码编写(ConsumerAckTest、ProducerAckTest），自己创建ConnectionFactory、Connection、Channel设置channel的各种属性 
案例也是有从网上参考，自己运行无问题。
