package com.example.damian.app_crm;

/*
Klasa abstrakcyjna do tworzenia karty (elementu) listy dla wyświetlanych danych
 */

public abstract class AItemList
{
    private String head;
    private String description;

    public AItemList(String head, String description)
    {
        this.head = head;
        this.description = description;
    }

    public String getHead()
    {
        return head;
    }

    public String getDescription()
    {
        return description;
    }
}
