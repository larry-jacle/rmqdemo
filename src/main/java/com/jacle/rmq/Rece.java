package com.jacle.rmq;

import com.jacle.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Rece
{
    public static void main(String[] args) throws  Exception
    {
        Connection conn= ConnectionUtils.getConnection();
        final Channel channel=conn.createChannel();

        String queuename="testdurable";
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
                System.out.println(new String(body,"UTF-8"));
                //multiple表示的是是否是消费多条信息,跟qos的设置有关
                //channel.basicAck(envelope.getDeliveryTag(), false);
                //拒绝服务端的消息，同时设置是否重新将消息放入队列
//                channel.basicReject(envelope.getDeliveryTag(), false);
                //重新放入队列，就会再次发送给consumner进行消费
                //rmq的模式是消息推送，push方式，而不是get方式
                  channel.basicReject(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(queuename, consumer);


    }
}
