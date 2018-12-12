package com.jacle.rmq;

import com.jacle.utils.ConnectionUtils;
import com.rabbitmq.client.*;
import org.springframework.amqp.core.ExchangeTypes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


/**
* 
* Created with Jacle
* Date:2018/8/21
* Time:11:24
* 
* */
public class HeadersRece
{

    public static void main(String[] args) throws Exception
    {
        Connection conn = ConnectionUtils.getConnection();
        final Channel channel = conn.createChannel();

        String exchangeName="exchangeHeaders";
        String queuename = "exchangeHeadersQueue";

        Map<String,Object> headers=new Hashtable<String,Object>();
        headers.put("x-match", "all");
        headers.put("name", "12345");
        headers.put("name2", "123456");

        //队列持久化
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.HEADERS, true);
        channel.queueDeclare(queuename, false, false, false, null);
        channel.queueBind(queuename, exchangeName, "", headers);
        //qos是消费端的确认
        channel.basicQos(1);


        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                System.out.println(new String(body,"UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(queuename, false,consumer);


    }
}
