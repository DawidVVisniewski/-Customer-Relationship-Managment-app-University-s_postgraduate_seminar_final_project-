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

public class CAdapterTaskList extends RecyclerView.Adapter<CViewHolderList>
{
    private List<CItemListTask> taskList;
    private ActivityMain currentActivity;
    private View currentView;

    public CAdapterTaskList(List<CItemListTask> taskList)
    {
        this.taskList = taskList;
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
        CItemListTask taskListItem = taskList.get(position);

        holder.getTvHead().setText(taskListItem.getHead());
        holder.getTvDescription().setText(taskListItem.getDescription());

        if(taskListItem.getTask().getFlagTask().equals("1"))
        {
           holder.getRlCardView().setBackgroundResource(R.color.colorCardViewTaskFlakTrue);
        }
        else
        {
            holder.getRlCardView().setBackgroundResource(R.color.colorCardViewTaskFlakFalse);
        }

        holder.getListItemOptions().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                PopupMenu popupMenu = new PopupMenu(currentView.getContext(), holder.getListItemOptions());

                popupMenu.getMenuInflater().inflate(R.menu.menu_list_item_task, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        int currentPosition = holder.getAdapterPosition() ;

                        if(item.getItemId() == R.id.i_delete_list_item)
                        {
                            CDataTask cDataTaskTemp = taskList.get(currentPosition).getTask();

                            new CAdapterTaskList.DeleteTask(currentActivity, cDataTaskTemp.getIdTask()).execute();

                            taskList.remove(currentPosition);

                            notifyItemRemoved(currentPosition);
                        }
                        else if(item.getItemId() == R.id.i_complet)
                        {
                            CDataTask cDataTaskTemp = taskList.get(currentPosition).getTask();

                            new CAdapterTaskList.UpdateTask(currentActivity, cDataTaskTemp.getIdTask(), "1").execute();

                            cDataTaskTemp.setFlagTask("1");

                            CItemListTask cItemTaskTemp = taskList.get(currentPosition);

                            cItemTaskTemp.setcDataTask(cDataTaskTemp);

                            taskList.set(currentPosition, cItemTaskTemp);

                            notifyItemChanged(currentPosition);
                        }
                        else if(item.getItemId() == R.id.i_to_complet)
                        {
                            CDataTask cDataTaskTemp = taskList.get(currentPosition).getTask();

                            new CAdapterTaskList.UpdateTask(currentActivity, cDataTaskTemp.getIdTask(), "0").execute();

                            cDataTaskTemp.setFlagTask("0");

                            CItemListTask cItemTaskTemp = taskList.get(currentPosition);

                            cItemTaskTemp.setcDataTask(cDataTaskTemp);

                            taskList.set(currentPosition, cItemTaskTemp);

                            notifyItemChanged(currentPosition);
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
        return taskList.size();
    }

    public class DeleteTask extends AsyncTask<Void, Void, Boolean>
    {
        private static final String PATH = "task/delete/";

        private Context context;

        private ProgressDialog processDialog;

        private int idTask;

        public DeleteTask(Context context, int idTask)
        {
            this.context = context;
            this.idTask = idTask;
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

            String id = idTask + "/";

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
                Toast.makeText(this.context, "Zadanie zostało usunięte", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.context, "Zadanie nie zostało usunięte", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class UpdateTask extends AsyncTask<Void, Void, Boolean>
    {
        private static final String PATH = "task/update/";

        private Context context;

        private ProgressDialog processDialog;

        private int idTask;

        private String newValueFlag;

        public UpdateTask(Context context, int idTask, String newValueFlag)
        {
            this.context = context;
            this.idTask = idTask;
            this.newValueFlag = newValueFlag;
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

            String id = idTask + "/";

            HashMap<String, String> postDataParams = new HashMap<String, String>();

            String linkedURL = CServerSettings.DOMAIN_ADDRESS + PATH + id;

            postDataParams.put("flag", newValueFlag);

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
                Toast.makeText(this.context, "Dane zadania zostały zmienione", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.context, "Dane zadania nie zostały zmienione", Toast.LENGTH_LONG).show();
            }
        }
    }
}