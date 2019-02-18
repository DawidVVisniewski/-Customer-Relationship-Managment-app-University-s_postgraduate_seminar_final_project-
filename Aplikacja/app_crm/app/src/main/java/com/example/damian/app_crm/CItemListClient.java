package com.example.damian.app_crm;

public class CItemListClient extends AItemList
{
    private CDataClient cDataClient;

    public CItemListClient(CDataClient cDataClient)
    {
        super(cDataClient.getName(), cDataClient.getDescription());

        this.cDataClient = cDataClient;
    }

    public CDataClient getClient()
    {
        return cDataClient;
    }
}
