package com.example.damian.app_crm;

public class CItemListTask extends AItemList
{
    private CDataTask cDataTask;

    public CItemListTask(CDataTask cDataTask)
    {
        super(cDataTask.getTitleTask(), cDataTask.getDescriptionTask());

        this.cDataTask = cDataTask;
    }

    public void setcDataTask(CDataTask cDataTask) {
        this.cDataTask = cDataTask;
    }

    public CDataTask getTask()
    {
        return cDataTask;
    }
}
