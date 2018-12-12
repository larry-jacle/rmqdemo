package com.jacle.delay;

import com.jacle.domain.RmqObj;
import com.jacle.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.util.SerializationUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RmqSendObj
{
    public static void main(String[] argses) throws IOException, TimeoutException
    {
        Connection conn = ConnectionUtils.getConnection();
        Channel channel = conn.createChannel();

        String exchangename="delayExchange";
        String queuename="delaySendQueue";

        //发送消息都发送到exchange
        channel.exchangeDeclare(exchangename, "direct", true);
        Map<String, Object> args=new HashMap<String, Object>();
        args.put("x-expires", 300000);//队列过期时间
        args.put("x-message-ttl", 12000);//队列上消息过期时间
        args.put("x-max-length", 5);//设置队列的长度
        args.put("x-dead-letter-exchange", "exchange-direct");//过期消息转向路由
        args.put("x-dead-letter-routing-key", "routing-delay");//过期消息转向路由相匹配routingk

        //队列持久化
        //设置死信消息的路由、队列、消息的过期时间
        channel.queueDeclare(queuename, true, false, false, args);

        //发送信息
        for(int i=0;i<6;i++)
        {
            RmqObj obj = new RmqObj();
            obj.setName("杰克,Jacle"+i);
            System.out.println("msg sent");
            channel.basicPublish(exchangename, "", null, SerializationUtils.serialize(obj));
        }

        channel.close();
        conn.close();
    }
}
