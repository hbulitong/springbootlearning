package com.example.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.rabbitmq.component.RabbitProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

	@Autowired
    private RabbitProducer producer;
	//生产者测试
    @Test
    public void testStringSend() {
        for (int i = 0; i < 10; i++) {
            producer.stringSend();
        }
    }
    
    @Test
    public void testFanoutSend() {
        producer.fanoutSend();
    }

}
