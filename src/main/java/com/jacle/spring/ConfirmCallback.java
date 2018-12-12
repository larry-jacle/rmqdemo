package com.jacle.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * Description:
 * author:Jacle
 * Date:2018/8/22
 * Time:14:23
 **/
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback
{
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause)
    {
       if(ack)
       {
           System.out.println("发送端确认");
       }else
       {
           System.out.println("发送端发送错误:"+cause);
       }

    }
}
