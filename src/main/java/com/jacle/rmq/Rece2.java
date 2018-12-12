package com.jacle.rmq;

import com.jacle.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;


public class Rece2
{

    public static void main(String[] args) throws Exception
    {
        Connection conn = ConnectionUtils.getConnection();
        final Channel channel = conn.createChannel();

        String queuename = "testdurable";
        //队列持久化
        channel.queueDeclare(queuename, true, false, false, null);
        //qos是消费端的确认
        channel.basicQos(1);

        GetResponse response = channel.basicGet(queuename, true);
        System.out.println(new String(response.getBody()));


    }
}
