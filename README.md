# springbootlearning
学习过程中的一些工程
1. async-springboot 异步处理
2. rabbitmq(生产者)和rabbitmqConsumer(消费者) 是spring boot 集成rabbitmq
3. rabbitmqAck 是加入了消息确认的机制，两种方式，一种是用RabbitTemplate的案例，另外一种是用原生的代码编写(ConsumerAckTest、ProducerAckTest），自己创建ConnectionFactory、Connection、Channel设置channel的各种属性 
案例也是有从网上参考，自己运行无问题。
4. MQ实际应用要考虑幂等性，幂等的基本定义为：对外提供的接口承诺幂等性，其要表达的含义是：只要调用接口成功，外部对接口的多次调用得到的结果是相同的。即执行多次和一次的效果是一样的。
