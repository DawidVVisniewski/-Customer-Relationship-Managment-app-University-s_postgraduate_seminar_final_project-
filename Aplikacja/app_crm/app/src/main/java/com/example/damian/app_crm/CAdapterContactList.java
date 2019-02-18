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

public class CAdapterContactList extends RecyclerView.Adapter<CViewHolderList>
{
    private List<CItemListContact> contactList;
    private ActivityMain currentActivity;
    private View currentView;

    public CAdapterContactList(List<CItemListContact> contactList)
    {
        this.contactList = contactList;
    }

    @Override
    public CViewHolderList onCreateViewHolder(ViewGroup parent, int viewType)
    {
        currentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        currentActivity = (ActivityMain) currentView.getContext();

        return new CViewHolderList(currentView);
    }

    @Override
    public void onBindViewHolder(final CViewHolderList holder, final int position)
    {
        CItemListContact contactListItem = contactList.get(position);

        holder.getTvHead().setText(contactListItem.getHead());
        holder.getTvDescription().setText(contactListItem.getDescription());

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
                            CDataContact cDataContactTemp = contactList.get(currentPosition).getContact();

                            new CAdapterContactList.DeleteContact(currentActivity, cDataContactTemp.getIdContact()).execute();

                            contactList.remove(currentPosition);

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
        return contactList.size();
    }

    public class DeleteContact extends AsyncTask<Void, Void, Boolean>
    {
        private static final String PATH = "contact/delete/";

        private Context context;

        private ProgressDialog processDialog;

        private boolean success;

        private int idContact;

        public DeleteContact(Context context, int idContact)
        {
            this.context = context;
            this.idContact = idContact;
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
            String id = idContact + "/";

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
                Toast.makeText(this.context, "Kontakt został usunięty", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.context, "Kontakt nie został usunięty", Toast.LENGTH_LONG).show();
            }
        }
    }
}