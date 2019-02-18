package com.example.damian.app_crm;

public class CDataClient
{
    private int id;
    private String name;
    private String type;
    private String description;

    public CDataClient(int id, String name, String type, String description)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public String getDescription()
    {
        return description;
    }

}
