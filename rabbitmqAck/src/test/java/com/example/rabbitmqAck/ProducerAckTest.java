package com.example.rabbitmqAck;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
//原生代码写，没有用RabbitTemplate
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerAckTest {
	@Test
	public void testProduec()  {
		ConnectionFactory factory=new ConnectionFactory();
		factory.setVirtualHost("/");
		factory.setHost("127.0.0.1");
		factory.setPort(AMQP.PROTOCOL.PORT);
		factory.setUsername("guest");
		factory.setPassword("guest");
		
		Connection connection;
		try {
			connection = factory.newConnection();
		   //创建连接
			Channel channel=connection.createChannel();    //创建通道
			String EXCHANGE_NAME = "exchange.direct";
		    String QUEUE_NAME = "queue_name";
		    String ROUTING_KEY = "key";
		    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);  //声明交换机和type
		    channel.queueDeclare(QUEUE_NAME, true, false, false, null);  //声明队列
		    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		    
		    String message="hello RabbitMq";
		    for(int i=0;i<5;i++) {
		    	channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, (message+i).getBytes("UTF-8"));
		    }
		    channel.close();
	        connection.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
		
			
			
	}
}
