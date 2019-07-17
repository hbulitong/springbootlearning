package com.example.rabbitmqAck.consumer;



import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;

import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
 
import java.io.IOException;
import java.util.Date;
import java.util.Map;

//备注：我们用注解的方式来接受消息 就不要用 自己创建对象实现ChannelAwareMessageListener的方式来接受消息 这种方式还要去全局里面配置 麻烦，直接用@RabbitListener(queues = "hello")最简单
//
//消息确认  因为我在属性配置文件里面开启了ACK确认 所以如果代码没有执行ACK确认 你在RabbitMQ的后台会看到消息会一直留在队列里面未消费掉 只要程序一启动开始接受该队列消息的时候 又会收到
//
// channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
////消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
//
//channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//
////ack返回false，并重新回到队列，api里面解释得很清楚
//
//channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//
////拒绝消息
//
//channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);


//channel.basicAck(deliveryTag, false);
//
//deliveryTag:该消息的index
//multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
//
//channel.basicNack(deliveryTag, false, true);
//
//deliveryTag:该消息的index
//multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
//requeue：被拒绝的是否重新入队列
//
//channel.basicReject(deliveryTag:, false);
//
//deliveryTag:该消息的index
//requeue：被拒绝的是否重新入队列

 
@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {
 
    @RabbitHandler
    public void process(String hello,Channel channel, Message message) throws IOException {
        System.out.println("HelloReceiver收到  : " + hello +"收到时间"+new Date());
        try {
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("receiver success");
        } catch (IOException e) {
            e.printStackTrace();
            //丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            System.out.println("receiver fail");
        }
 
    }
}
