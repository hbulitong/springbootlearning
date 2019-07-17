package com.example.rabbitmqAck;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.rabbitmqAck.service.HelloSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqAckApplicationTests {

	@Autowired
    private HelloSender helloSender;
	@Test
	public void contextLoads() {
		helloSender.send();
	}

}
