package com.example.damian.app_crm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CAdapterActivityList extends RecyclerView.Adapter<CViewHolderList>
{
    private List<CItemListActivity> activityList;

    public CAdapterActivityList(List<CItemListActivity> activityList)
    {
        this.activityList = activityList;
    }

    @Override
    public CViewHolderList onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new CViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(final CViewHolderList holder, final int position)
    {
        CItemListActivity activityListItemTemp = activityList.get(position);

        holder.getTvHead().setText(activityListItemTemp.getHead());
        holder.getTvDescription().setText(activityListItemTemp.getDescription());

        CDataActivity cDataActivityTemp = activityListItemTemp.getActivity();
        String typeActivityTemp = cDataActivityTemp.getTypeActivity();

        if(typeActivityTemp.equals("klient"))
        {
            holder.getListItemOptions().setImageResource(R.mipmap.ic_new_client);
        }
        else if(typeActivityTemp.equals("notatka"))
        {
            holder.getListItemOptions().setImageResource(R.mipmap.ic_meet);
        }
        else if(typeActivityTemp.equals("zadanie"))
        {
            holder.getListItemOptions().setImageResource(R.mipmap.ic_task);
        }
        else if(typeActivityTemp.equals("kontakt"))
        {
            holder.getListItemOptions().setImageResource(R.mipmap.ic_contact);
        }
        else if(typeActivityTemp.equals("spotkanie"))
        {
            holder.getListItemOptions().setImageResource(R.mipmap.ic_meet);
        }
    }

    @Override
    public int getItemCount()
    {
        return activityList.size();
    }
}