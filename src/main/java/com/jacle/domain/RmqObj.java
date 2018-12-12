package com.jacle.domain;

import java.io.Serializable;

/**
 * Description:
 * author:Jacle
 * Date:2018/8/21
 * Time:14:41
 **/
public class RmqObj  implements Serializable
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
