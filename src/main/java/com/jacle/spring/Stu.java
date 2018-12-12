package com.jacle.spring;

import java.io.Serializable;

/**
 * Description:
 * author:Jacle
 * Date:2018/8/22
 * Time:9:14
 **/
public class Stu implements Serializable
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
