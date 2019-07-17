package com.example.rabbitmqAck;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
//原生代码写，没有用RabbitTemplate
//deliveryTag 消息索引下标从1开始
//Total代表队列中的消息总条数，Ready代表消费者还可以读到的条数，Unacked:代表还有多少条没有被应答 
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerAckTest {
	@Test
    public void testBasicConsumer1() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setHost("127.0.0.1");
        factory.setPort(AMQP.PROTOCOL.PORT);    // 5672
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        String EXCHANGE_NAME = "exchange.direct";
        String QUEUE_NAME = "queue_name";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "key");
        channel.basicQos(1);  //设置每次获取一条消息


//        GetResponse response = channel.basicGet(QUEUE_NAME, false);
//        byte[] body = response.getBody();
//        System.out.println(new String(body).toString());

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(message);
                if (message.contains("3")){
                    // requeue：重新入队列，false：直接丢弃，相当于告诉队列可以直接删除掉
                	//requeue=true,表示将消息重新放入到队列中，false：表示直接从队列中删除，此时和basicAck(long deliveryTag, false)的效果一样
                	//void basicReject(long deliveryTag, boolean requeue);
                    channel.basicReject(envelope.getDeliveryTag(), true);   //由于设置了每次获取一条消息，但是到包含3的时候会拒绝然后重新加入队列，会导致死循环，一致消费这条消息
                } else {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

                //channel.basicAck(envelope.getDeliveryTag(), false);  //手动确认
            }
        };

        channel.basicConsume(QUEUE_NAME, false, consumer);  //自动确认配置为false

        Thread.sleep(100000);
    }

}
