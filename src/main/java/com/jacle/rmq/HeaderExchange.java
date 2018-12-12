package com.jacle.rmq;

import com.jacle.utils.ConnectionUtils;
import com.rabbitmq.client.*;
import com.rabbitmq.http.client.domain.ExchangeType;
import org.springframework.amqp.core.ExchangeTypes;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class HeaderExchange
{
    public static void main(String[] args) throws  Exception
    {
        Connection conn= ConnectionUtils.getConnection();
        Channel channel=conn.createChannel();

        String exchangeName="exchangeHeaders";
        String queuename="exchangeHeadersQueue";
        //队列持久化
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.HEADERS,true);
        channel.queueDeclare(queuename, false, false, false, null);

        //新建headers,headers的value必须是object
        Map<String,Object> headers=new Hashtable<String,Object>();
        headers.put("testkey", "testvalue");
        headers.put("testkey2", "testvalue");
        headers.put("name", "12345");


        //新建builder，添加headers
        AMQP.BasicProperties.Builder builder=new AMQP.BasicProperties.Builder();
        builder.headers(headers);
        //2表示持久化
        builder.deliveryMode(2);

        channel.basicPublish(exchangeName, queuename,builder.build() , "msg".getBytes());

        channel.close();
        conn.close();

    }
}
