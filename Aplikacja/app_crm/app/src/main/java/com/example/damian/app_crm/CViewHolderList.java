package com.example.damian.app_crm;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CViewHolderList extends RecyclerView.ViewHolder
{
    private TextView tvHead;
    private TextView tvDescription;
    private ImageButton ibListItemOptions;
    private RelativeLayout rlCardView;

    private View currentView;

    public CViewHolderList(final View itemView)
    {
        super(itemView);

        tvHead = (TextView) itemView.findViewById(R.id.tv_head_list_item);
        tvDescription = (TextView) itemView.findViewById(R.id.tv_description_list_item);
        ibListItemOptions = (ImageButton) itemView.findViewById(R.id.ib_list_item_options);
        rlCardView = (RelativeLayout) itemView.findViewById(R.id.rl_card_view);

        currentView = itemView;
    }

    public TextView getTvHead() {
        return tvHead;
    }

    public TextView getTvDescription()
    {
        return tvDescription;
    }

    public ImageButton getListItemOptions()
    {
        return ibListItemOptions;
    }

    public RelativeLayout getRlCardView()
    {
        return rlCardView;
    }

    public View getCurrentView() {
        return currentView;
    }
}