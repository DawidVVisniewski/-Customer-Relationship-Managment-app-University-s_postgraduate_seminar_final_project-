package com.example.damian.app_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.damian.app_crm.output.HttpConnectionService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class FragmentMainClientList extends Fragment implements View.OnClickListener
{
    private FloatingActionButton bAddClient;

    private String loginUser;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list, container, false);

        bAddClient = (FloatingActionButton) view.findViewById(R.id.b_add_client);

        bAddClient.setOnClickListener(this);

        this.updateLoginUser();

        new ListClientsTask(view, this).execute();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Lista klientów");
    }


    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.b_add_client)
        {
            ActivityMain mainActivity = (ActivityMain) v.getContext();

            Fragment nextFragment = new FragmentFormClient();

            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.clCoordinator, nextFragment).addToBackStack(null).commit();
        }
    }

    private void updateLoginUser()
    {
        loginUser = getActivity().getIntent().getStringExtra("loginUser");
    }

    private class ListClientsTask extends AsyncTask<Void, Void, Boolean>
    {
        private static final String PATH = "client/list?";

        private View currentView;
        private Fragment fragment;
        private Context context;

        private ProgressDialog processDialog;
        private RecyclerView recyclerView;

        private JSONArray arrayJSON;

        public ListClientsTask(View currentView, Fragment fragment)
        {
            this.currentView = currentView;
            this.fragment = fragment;

            context = fragment.getContext();
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

            String login = "login=" + loginUser;

            HashMap<String, String> postDataParams = new HashMap<String, String>();

            postDataParams.put("HTTP_ACCEPT", "application/json");

            String linkedURL = CServerSettings.DOMAIN_ADDRESS + PATH + login;

            HttpConnectionService service = new HttpConnectionService();

            String response = service.sendRequest(linkedURL, postDataParams);

            try
            {
                if(response.isEmpty() == false)
                {
                    JSONObject objectJSON = new JSONObject(response);

                    arrayJSON = objectJSON.getJSONArray("account");

                    success = true;
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
                if(arrayJSON != null)
                {
                    ArrayList clientList = new ArrayList<>();

                    for(int i = 0; i < arrayJSON.length(); i++)
                    {
                        try
                        {
                            JSONObject objectJSON = arrayJSON.getJSONObject(i);

                            int id = Integer.parseInt(objectJSON.getString("idClient"));
                            String name = objectJSON.getString("nameClient");
                            String type = objectJSON.getString("typeClient");
                            String description = objectJSON.getString("descriptionClient");

                            CDataClient cDataClientItem = new CDataClient(id, name, type, description);

                            CItemListClient cClientListItem = new CItemListClient(cDataClientItem);

                            clientList.add(cClientListItem);
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    RecyclerView.Adapter adapter = new CAdapterClientList(clientList);

                    recyclerView = (RecyclerView) currentView.findViewById(R.id.rv_list);

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getActivity()));
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }
}

