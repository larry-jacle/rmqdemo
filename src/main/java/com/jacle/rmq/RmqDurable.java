package com.jacle.rmq;

import com.jacle.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RmqDurable
{
    public static void main(String[] args) throws IOException, TimeoutException
    {
        Connection conn = ConnectionUtils.getConnection();
        Channel channel = conn.createChannel();

        String queuename = "testdurable";
        //队列持久化
        channel.queueDeclare(queuename, true, false, false, null);

        //新建headers,headers的value必须是object
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("testkey", "testvalue");
        //持久化
        headers.put("deliverMode", 2);

        //新建builder
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(2);
        //设置消息过期,过期了就没有了
        builder.expiration("5000");

        channel.basicPublish("", queuename,null, "msg-durable".getBytes());
        channel.basicPublish("", queuename,builder.build() , "msg".getBytes());
        channel.basicPublish("", queuename,null, "msg-durable".getBytes());
//        channel.basicPublish("", queuename, builder.build(), "msg2".getBytes());

        channel.close();
        conn.close();
    }
}
