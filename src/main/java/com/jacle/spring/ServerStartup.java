package com.jacle.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Description:
 * author:Jacle
 * Date:2018/8/22
 * Time:9:02
 **/
public class ServerStartup
{
    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:context.xml");
    }
}
