package com.example.damian.app_crm;

public class CItemListMeet extends AItemList
{
    private CDataMeet cDataMeet;

    public CItemListMeet(CDataMeet cDataMeet)
    {
        super(cDataMeet.getDateMeet() + " / " + cDataMeet.getTimeMeet(), cDataMeet.getLocationMeet());

        this.cDataMeet = cDataMeet;
    }

    public CDataMeet getMeet()
    {
        return cDataMeet;
    }
}
