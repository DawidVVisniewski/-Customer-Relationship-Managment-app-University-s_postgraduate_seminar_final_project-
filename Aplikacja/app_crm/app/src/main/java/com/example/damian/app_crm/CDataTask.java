package com.example.damian.app_crm;

public class CDataTask
{
    private int idTask;
    private String titleTask;
    private String descriptionTask;
    private String flagTask;
    private int idClient;

    public CDataTask(int idTask, String titleTask, String descriptionTask, String flagTask, int idClient)
    {
        this.idTask = idTask;
        this.titleTask = titleTask;

        this.descriptionTask = descriptionTask;

        if(descriptionTask == null || descriptionTask.equals("null") || descriptionTask.equals(""))
        {
            this.descriptionTask = "brak";
        }

        this.flagTask = flagTask;
        this.idClient = idClient;
    }

    public void setFlagTask(String flagTask) {
        this.flagTask = flagTask;
    }

    public String getFlagTask()
    {
        return flagTask;
    }
    public int getIdTask()
    {
        return idTask;
    }

    public String getTitleTask()
    {
        return titleTask;
    }

    public String getDescriptionTask()
    {
        return descriptionTask;
    }

    public int getIdClient()
    {
        return idClient;
    }
}
