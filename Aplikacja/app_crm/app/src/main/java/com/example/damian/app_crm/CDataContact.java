package com.example.damian.app_crm;

public class CDataContact
{
    private int idContact;
    private String nameContact;
    private String surnameContact;
    private String phoneContact;
    private int idClient;

    public CDataContact(int idContact, String nameContact, String surnameContact, String phoneContact, int idClient)
    {
        this.idContact = idContact;
        this.nameContact = nameContact;
        this.surnameContact = surnameContact;
        this.phoneContact = phoneContact;
        this.idClient = idClient;
    }

    public int getIdContact()
    {
        return idContact;
    }

    public String getNameContact()
    {
        return nameContact;
    }

    public String getSurnameContact()
    {
        return surnameContact;
    }

    public String getPhoneContact()
    {
        return phoneContact;
    }

    public int getIdClient()
    {
        return idClient;
    }
}
