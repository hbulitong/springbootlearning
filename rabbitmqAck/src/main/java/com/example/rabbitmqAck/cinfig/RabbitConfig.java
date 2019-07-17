package com.example.rabbitmqAck.cinfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;

/**
 * rabbitConfig
 * @author hbulitong
 *
 */
@Configuration
public class RabbitConfig {
	@Bean
	public Queue QueueA() {
		return new Queue("hello");
	}
	@Bean
	public Queue QueueB() {
		return new Queue("helloObj");
	}
	//fanout 广播模式
	//创建交换机
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange("AckExchange");
	}
	//建立exchange和queue的绑定关系
	Binding bindingExchangeA() {
		return BindingBuilder.bind(QueueA()).to(fanoutExchange());
	}
	Binding bindingExchangeB() {
		return BindingBuilder.bind(QueueB()).to(fanoutExchange());
	}
	
	
}
