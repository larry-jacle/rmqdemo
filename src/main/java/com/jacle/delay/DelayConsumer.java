package com.jacle.delay;

import com.jacle.domain.RmqObj;
import com.jacle.utils.ConnectionUtils;
import com.rabbitmq.client.*;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * author:Jacle
 * Date:2018/8/22
 * Time:16:52
 **/
public class DelayConsumer
{
    public static void main(String[] argses) throws  Exception
    {
        Connection conn= ConnectionUtils.getConnection();
        final Channel channel=conn.createChannel();

        String queuename="deadQueue";
        String exchangename="exchange-direct";

        //队列持久化
        channel.queueDeclare(queuename, true, false, false, null);
        channel.exchangeDeclare(exchangename, "direct", true);
        channel.queueBind(queuename, exchangename, "routing-delay");

        //qos是消费端的确认
        channel.basicQos(1);
        //没有这个队列就报异常
        //System.out.println(channel.queueDeclarePassive(queuename+"1"));
        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                System.out.println("##get##");
                RmqObj rmqObj=(RmqObj) SerializationUtils.deserialize(body);
                System.out.println(rmqObj.getName());

                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(queuename, consumer);

    }
}
