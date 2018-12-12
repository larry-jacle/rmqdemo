package com.jacle.rmq;

import com.jacle.domain.RmqObj;
import com.jacle.utils.ConnectionUtils;
import com.rabbitmq.client.*;
import org.springframework.util.SerializationUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RmqSendObj
{
    public static void main(String[] args) throws IOException, TimeoutException
    {
        Connection conn = ConnectionUtils.getConnection();
        Channel channel = conn.createChannel();

        String queuename = "objQueue";
        //队列持久化
        channel.queueDeclare(queuename, true, false, false, null);
        RmqObj obj = new RmqObj();
        obj.setName("杰克,Jacle");

        //通过对象流来进行操作，对象流是一个包装流
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);

        //对象数组
        byte[] objByteArr=byteArrayOutputStream.toByteArray();
        channel.basicPublish("", queuename,null,objByteArr);
        channel.basicPublish("", queuename, null, SerializationUtils.serialize(obj));

        byteArrayOutputStream.close();
        objectOutputStream.close();
        channel.close();
        conn.close();
    }
}
