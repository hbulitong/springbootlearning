package com.example.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.rabbitmq.component.RabbitProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopTest {
	@Autowired
    private RabbitProducer producer;
	@Test
    public void testTopic() {
        producer.topicTopic1Send();
        producer.topicTopic2Send();
        producer.topicTopic3Send();
    }
}
