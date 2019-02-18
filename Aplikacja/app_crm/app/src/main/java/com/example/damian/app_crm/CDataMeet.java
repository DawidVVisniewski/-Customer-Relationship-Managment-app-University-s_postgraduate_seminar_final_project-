package com.example.damian.app_crm;

public class CDataMeet
{
    private int idMeet;
    private String dateMeet;
    private String timeMeet;
    private String locationMeet;
    private int idClient;

    public CDataMeet(int idMeet, String dateMeet, String timeMeet, String locationMeet, int idClient)
    {
        this.idMeet = idMeet;
        this.dateMeet = dateMeet;
        this.timeMeet = timeMeet;
        this.locationMeet = locationMeet;
        this.idClient = idClient;
    }

    public int getIdMeet()
    {
        return idMeet;
    }

    public String getDateMeet()
    {
        return dateMeet;
    }

    public String getTimeMeet()
    {
        return timeMeet;
    }

    public String getLocationMeet()
    {
        return locationMeet;
    }

    public int getIdClient()
    {
        return idClient;
    }
}
