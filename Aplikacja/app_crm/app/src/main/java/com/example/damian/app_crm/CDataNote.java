package com.example.damian.app_crm;


public class CDataNote
{
    private int idNote;
    private String titleNote;
    private String descriptionNote;
    private int idClient;

    public CDataNote(int idNote, String titleNote, String descriptionNote, int idClient)
    {
        this.idNote = idNote;
        this.titleNote = titleNote;
        this.descriptionNote = descriptionNote;
        this.idClient = idClient;
    }

    public int getIdNote()
    {
        return idNote;
    }

    public String getTitleNote()
    {
        return titleNote;
    }

    public String getDescriptionNote()
    {
        return descriptionNote;
    }

    public int getIdClient()
    {
        return idClient;
    }
}
