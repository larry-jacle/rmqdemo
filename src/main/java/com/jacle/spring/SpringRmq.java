package com.jacle.spring;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Description:
 * author:Jacle
 * Date:2018/8/21
 * Time:17:01
 **/
public class SpringRmq
{
    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:context.xml");
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
        Stu stu=new Stu();
        stu.setName("杰克");
        //不配置convertor，直接发送那边接收的是对象的序列化字符串,默认传输对象使用的是序列化
        //可以配置为json设置
        amqpTemplate.convertAndSend(stu);

    }
}
