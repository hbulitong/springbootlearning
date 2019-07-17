package com.example.rabbitmqAck.service;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloSender implements RabbitTemplate.ReturnCallback{
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void send() {
        String context = "你好现在是 " + new Date() +"";
        System.out.println("HelloSender发送内容 : " + context);
//        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("HelloSender消息发送失败" + cause + correlationData.toString());
            } else {
                System.out.println("HelloSender 消息发送成功 ");
            }
        });
        this.rabbitTemplate.convertAndSend("hello", context);
    }

	 public void sendObj() {
//	       MessageObj obj = new MessageObj();
//	       obj.setACK(false);
//	       obj.setId(123);
//	       obj.setName("zhangsan");
//	       obj.setValue("data");
	       JSONObject obj=new JSONObject();
	       try {
	    	   obj.put("ACK", false);
	    	   obj.put("ID", 123);
	    	   obj.put("Name", "zhangsan");
	    	   obj.put("Value", "data");
		   } catch (JSONException e) {
				e.printStackTrace();
		   }
	       System.out.println("发送 : " + obj.toString());
	       this.rabbitTemplate.convertAndSend("helloObj", obj);
	    }

	
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		System.out.println("sender return success" + message.toString()+"==="+replyCode+"==="+replyText+"==="+exchange+"==="+routingKey);
		
	}
	
}
