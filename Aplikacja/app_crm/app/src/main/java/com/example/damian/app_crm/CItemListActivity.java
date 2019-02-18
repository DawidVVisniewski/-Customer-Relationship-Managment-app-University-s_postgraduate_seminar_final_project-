package com.example.damian.app_crm;

public class CItemListActivity extends AItemList
{
    private CDataActivity cDataActivity;

    public CItemListActivity(CDataActivity cDataActivity)
    {
        super(cDataActivity.getTitleActivity(), cDataActivity.getDateTimeActivity());

        this.cDataActivity = cDataActivity;
    }

    public CDataActivity getActivity()
    {
        return cDataActivity;
    }
}
