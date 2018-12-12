package com.jacle.delay;

import com.jacle.domain.RmqObj;
import com.jacle.utils.ConnectionUtils;
import com.rabbitmq.client.*;
import org.springframework.util.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public class ReceObj
{
    public static void main(String[] argses) throws  Exception
    {
        Connection conn= ConnectionUtils.getConnection();
        final Channel channel=conn.createChannel();

        String queuename="delaySendQueue";
        String exchangename="delayExchange";

        Map<String, Object> args=new HashMap<String, Object>();
        args.put("x-expires", 300000);//队列过期时间
        args.put("x-message-ttl", 12000);//队列上消息过期时间
        args.put("x-max-length", 5);//设置队列的长度
        args.put("x-dead-letter-exchange", "exchange-direct");//过期消息转向路由
        args.put("x-dead-letter-routing-key", "routing-delay");//过期消息转向路由相匹配routingk

        //队列持久化
        //设置死信消息的路由、队列、消息的过期时间
        channel.queueDeclare(queuename, true, false, false, args);
        channel.queueBind(queuename, exchangename, "",null );
        //qos是消费端的确认
        channel.basicQos(1);
        //没有这个队列就报异常
        //System.out.println(channel.queueDeclarePassive(queuename+"1"));
        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("##get##");
                RmqObj rmqObj=(RmqObj) SerializationUtils.deserialize(body);
                System.out.println(rmqObj.getName());

                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(queuename, consumer);


    }
}
