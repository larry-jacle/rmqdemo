package com.jacle.spring;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.io.UnsupportedEncodingException;

/**
 * Description:
 * author:Jacle
 * Date:2018/8/22
 * Time:8:56
 **/
public class Receiver implements ChannelAwareMessageListener
{
    public void onMessage(Message message)
    {
        try
        {
            System.out.println(new String(message.getBody(),"UTF-8"));
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
//            Stu stu=(Stu)SerializationUtils.deserialize(message.getBody());
//            System.out.println(stu.getName());

    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception
    {
        try
        {
            System.out.println(new String(message.getBody(),"UTF-8"));
            //设置手动，为发送ack，程序默认的是接收一次，管理端将消息变为了ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

    }
}
