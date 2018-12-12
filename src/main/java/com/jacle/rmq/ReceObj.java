package com.jacle.rmq;

import com.jacle.domain.RmqObj;
import com.jacle.utils.ConnectionUtils;
import com.rabbitmq.client.*;
import org.springframework.util.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReceObj
{
    public static void main(String[] args) throws  Exception
    {
        Connection conn= ConnectionUtils.getConnection();
        final Channel channel=conn.createChannel();

        String queuename="objQueue";
        //队列持久化
        channel.queueDeclare(queuename, true, false, false, null);
        //qos是消费端的确认
        channel.basicQos(1);
        //没有这个队列就报异常
        //System.out.println(channel.queueDeclarePassive(queuename+"1"));
        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("##get##");
                ByteArrayInputStream inputStream=new ByteArrayInputStream(body);
                ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
           /*     try
                {
                    RmqObj rmqObj=(RmqObj) objectInputStream.readObject();
                    System.out.println(rmqObj.getName());
                } catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }*/
                RmqObj rmqObj=(RmqObj) SerializationUtils.deserialize(body);
                System.out.println(rmqObj.getName());

                inputStream.close();
                objectInputStream.close();
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(queuename, consumer);


    }
}
