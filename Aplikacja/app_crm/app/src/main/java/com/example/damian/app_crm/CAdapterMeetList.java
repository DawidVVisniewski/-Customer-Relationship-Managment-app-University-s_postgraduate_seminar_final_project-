package com.example.damian.app_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.damian.app_crm.output.HttpConnectionService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class CAdapterMeetList extends RecyclerView.Adapter<CViewHolderList>
{
    private List<CItemListMeet> meetList;
    private ActivityMain currentActivity;
    private View currentView;

    public CAdapterMeetList(List<CItemListMeet> meetList)
    {
        this.meetList = meetList;
    }

    @Override
    public CViewHolderList onCreateViewHolder(ViewGroup parent, int viewType)
    {
        currentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        currentActivity = (ActivityMain) currentView.getContext();

        return new CViewHolderList(currentView);
    }

    @Override
    public void onBindViewHolder(final CViewHolderList holder, int position)
    {
        CItemListMeet meetListItem = meetList.get(position);

        holder.getTvHead().setText(meetListItem.getHead());
        holder.getTvDescription().setText(meetListItem.getDescription());

        holder.getListItemOptions().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                PopupMenu popupMenu = new PopupMenu(currentView.getContext(), holder.getListItemOptions());

                popupMenu.getMenuInflater().inflate(R.menu.menu_list_item, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        int currentPosition = holder.getAdapterPosition() ;

                        if(item.getItemId() == R.id.i_delete_list_item)
                        {
                            CDataMeet cDataMeetTemp = meetList.get(currentPosition).getMeet();

                            new CAdapterMeetList.DeleteMeet(currentActivity, cDataMeetTemp.getIdMeet()).execute();

                            meetList.remove(currentPosition);

                            notifyItemRemoved(currentPosition);
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return meetList.size();
    }

    public class DeleteMeet extends AsyncTask<Void, Void, Boolean>
    {
        private static final String PATH = "meet/delete/";

        private Context context;

        private ProgressDialog processDialog;

        private int idMeet;

        public DeleteMeet(Context context, int idMeet)
        {
            this.context = context;
            this.idMeet = idMeet;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            processDialog = new ProgressDialog(context);

            processDialog.setMessage("Proszę czekać...");
            processDialog.setCancelable(false);
            processDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0)
        {
            boolean success;

            String id = idMeet + "/";

            HashMap<String, String> postDataParams = new HashMap<String, String>();

            postDataParams.put("HTTP_ACCEPT", "application/json");

            String linkedURL = CServerSettings.DOMAIN_ADDRESS + PATH + id;

            HttpConnectionService service = new HttpConnectionService();

            String response = service.sendRequest(linkedURL, postDataParams);

            try
            {
                if(response.isEmpty() == false)
                {
                    JSONObject objectJSON = new JSONObject(response);

                    String downloadResult = objectJSON.getString("status");

                    success = Boolean.parseBoolean(downloadResult);
                }
                else
                {
                    success = false;
                }
            }
            catch(JSONException e)
            {
                success = false;

                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            super.onPostExecute(success);

            if (processDialog.isShowing())
            {
                processDialog.dismiss();
            }

            if(success)
            {
                Toast.makeText(this.context, "Termin spotkania został usunięty", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.context, "Termin spotkania nie został usunięty", Toast.LENGTH_LONG).show();
            }
        }
    }
}