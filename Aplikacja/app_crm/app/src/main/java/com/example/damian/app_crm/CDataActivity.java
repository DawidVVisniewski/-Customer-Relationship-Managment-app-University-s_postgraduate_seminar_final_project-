package com.example.damian.app_crm;

public class CDataActivity
{
    private int idActivity;
    private String dateTimeActivity;
    private String titleActivity;
    private String typeActivity;
    private int idClient;

    public CDataActivity(int idActivity, String dateTimeActivity, String titleActivity, String typeActivity, int idClient)
    {
        this.idActivity = idActivity;
        this.dateTimeActivity = dateTimeActivity;
        this.titleActivity = titleActivity;
        this.typeActivity = typeActivity;
        this.idClient = idClient;
    }

    public int getIdActivity()
    {
        return idActivity;
    }

    public String getDateTimeActivity()
    {
        return dateTimeActivity;
    }

    public String getTitleActivity()
    {
        return titleActivity;
    }

    public String getTypeActivity()
    {
        return typeActivity;
    }

    public int getIdClient()
    {
        return idClient;
    }
}
