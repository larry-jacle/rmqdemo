package com.jacle.utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {
	
	/**
	 * 获取MQ的连接  
	 * @return
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static Connection  getConnection() throws IOException, TimeoutException{
		//定义一个连接工厂
		ConnectionFactory factory =new ConnectionFactory();
		
		//设置服务地址
		factory.setHost("192.168.17.9");

		//AMQP 5672
		factory.setPort(5672);
		
		//vhost 
		factory.setVirtualHost("/");
		
		//用户名 
		factory.setUsername("admin");
		
		//密码
		factory.setPassword("admin");
		return factory.newConnection();
	}
	
}