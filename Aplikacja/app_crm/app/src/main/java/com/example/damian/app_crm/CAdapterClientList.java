package com.example.damian.app_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
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

public class CAdapterClientList extends RecyclerView.Adapter<CViewHolderList>
{
    private List<CItemListClient> clientsList;

    private ActivityMain currentActivity;
    private View currentView;

    private int currentPosition;

    public CAdapterClientList(List<CItemListClient> clientList)
    {
        this.clientsList = clientList;
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
        CItemListClient clientsListItem = clientsList.get(position);

        currentPosition = position;

        holder.getTvHead().setText(clientsListItem.getHead());
        holder.getTvDescription().setText(clientsListItem.getDescription());

        /*
        ustawienie słuchacza na przycisku w karcie listy
         */

        holder.getListItemOptions().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PopupMenu popupMenu = new PopupMenu(currentView.getContext(), holder.getListItemOptions());

                popupMenu.getMenuInflater().inflate(R.menu.menu_list_item, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        int currentPosition = holder.getAdapterPosition();

                        if(item.getItemId() == R.id.i_delete_list_item)
                        {
                            CDataClient cDataClientTemp = clientsList.get(currentPosition).getClient();

                            new DeleteClient(currentActivity, cDataClientTemp.getId()).execute();

                            /*
                            odświeżanie widoku listy po usunięciu klienta
                             */

                            clientsList.remove(currentPosition);

                            notifyItemRemoved(currentPosition);
                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        /*
        ustawienie słuchacza na karcie - elemencie listy
         */

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*
                przekazywanie damych do kolejnej aktywności
                 */

                CDataClient cDataClientTemp = clientsList.get(position).getClient();

                currentActivity.getIntent().putExtra("idClient", cDataClientTemp.getId() + "");
                currentActivity.getIntent().putExtra("nameClient", cDataClientTemp.getName());
                currentActivity.getIntent().putExtra("typeClient", cDataClientTemp.getType());
                currentActivity.getIntent().putExtra("descriptionClient", cDataClientTemp.getDescription());

                Fragment nextFragment = new FragmentMainClientPanel();

                currentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.clCoordinator, nextFragment).addToBackStack(null).commit();
            }
        });
    }

    /*
    metoda getItemCount zwrawca liczbę elementów listy
     */

    @Override
    public int getItemCount()
    {
        return clientsList.size();
    }

    /*
    Zdarzenia asynchroniczne AsyncTask wykorzystane do wykonywania działań czasochłonnych w tym wypadku do usunięcia klienta ze serwera.
     */

    public class DeleteClient extends AsyncTask<Void, Void, Boolean>
    {
        private static final String PATH = "client/delete/";

        private Context context;
        private ProgressDialog processDialog;

        private int idClient;

        public DeleteClient(Context context, int idClient)
        {
            this.context = context;
            this.idClient = idClient;
        }

        /*
        metoda onPreExecute wywoływana przed uruchomieniem właściwego zadania
         */

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            /*
            konfiguracja interfejsu w tym wypadku okna ładowania, informującego użytkownika o tym że musi poczekać aż czynność się wykona
             */

            processDialog = new ProgressDialog(context);

            processDialog.setMessage("Proszę czekać...");
            processDialog.setCancelable(false);
            processDialog.show();
        }

        /*
        metoda doInBackground uruchamiana jest w oddzielnym wątku
         */

        @Override
        protected Boolean doInBackground(Void... arg0)
        {
            boolean success;

            String id = idClient + "/";

            HashMap<String, String> postDataParams = new HashMap<String, String>();

            postDataParams.put("HTTP_ACCEPT", "application/json");

            String linkedURL = CServerSettings.DOMAIN_ADDRESS + PATH + id;

            HttpConnectionService service = new HttpConnectionService();

            /*
            pobranie wartości zwracanej przez serwer po usunięciu klienta
             */

            String response = service.sendRequest(linkedURL, postDataParams);

            try
            {
                if(response.isEmpty() == false)
                {
                    /*
                    parsowanie danych zwróconych przez serwer tj. w formacie JSON
                     */

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

        /*
        metoda wywoływana w momencie zakończenia pracy metody doInBackground
         */

        @Override
        protected void onPostExecute(final Boolean success)
        {
            super.onPostExecute(success);

            if(processDialog.isShowing())
            {
                processDialog.dismiss();
            }

            /*
            wartość przekazana przez tą metodą ustawiana jest w metodzie doInBackground i jest przez nią zwracana
             */

            if(success)
            {
                Toast.makeText(this.context, "Klient został usunięty", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.context, "Klient nie został usunięty", Toast.LENGTH_LONG).show();
            }
        }
    }
}