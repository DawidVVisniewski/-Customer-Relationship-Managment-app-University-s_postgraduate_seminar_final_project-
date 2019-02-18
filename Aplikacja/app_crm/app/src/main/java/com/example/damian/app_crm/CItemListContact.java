package com.example.damian.app_crm;

public class CItemListContact extends AItemList
{
    private CDataContact cDataContact;

    public CItemListContact(CDataContact cDataContact)
    {
        super(cDataContact.getNameContact() + " " + cDataContact.getSurnameContact(), cDataContact.getPhoneContact());

        this.cDataContact = cDataContact;
    }

    public CDataContact getContact()
    {
        return cDataContact;
    }
}
