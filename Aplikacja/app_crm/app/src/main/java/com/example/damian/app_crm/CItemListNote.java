package com.example.damian.app_crm;

public class CItemListNote extends AItemList
{
    private CDataNote cDataNote;

    public CItemListNote(CDataNote cDataNote)
    {
        super(cDataNote.getTitleNote(), cDataNote.getDescriptionNote());

        this.cDataNote = cDataNote;
    }

    public CDataNote getNote()
    {
        return cDataNote;
    }
}
