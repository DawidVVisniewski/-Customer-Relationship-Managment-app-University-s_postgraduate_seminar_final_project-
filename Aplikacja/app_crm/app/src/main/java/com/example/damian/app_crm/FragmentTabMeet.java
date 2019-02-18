package com.example.damian.app_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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

public class FragmentTabMeet extends Fragment
{
    private FloatingActionButton bAddmeet;

    private String idClient;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tab_meet, container, false);

        bAddmeet = (FloatingActionButton) view.findViewById(R.id.b_add_meet);

        bAddmeet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ActivityMain activity = (ActivityMain) view.getContext();

                activity.setLastFragment("3");

                Fragment myFragment = new FragmentFormMeet();

                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.clCoordinator, myFragment).addToBackStack(null).commit();
            }
        });

        this.getIdClient();

        new ListMeetTask(view, this).execute();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getIdClient()
    {
        idClient = getActivity().getIntent().getStringExtra("idClient");
    }

    private class ListMeetTask extends AsyncTask<Void, Void, Boolean>
    {
        private static final String PATH = "meet/list?";

        private View view ;
        private Fragment fragment;
        private Context context;

        private ProgressDialog processDialog;
        private RecyclerView recyclerView;

        private boolean success;

        private JSONArray arrayJSON;

        public ListMeetTask(View view , Fragment fragment)
        {
            this.view = view;
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

            String id = "id=" + idClient;

            HashMap<String, String> postDataParams = new HashMap<String, String>();

            postDataParams.put("HTTP_ACCEPT", "application/json");

            String linkedURL = CServerSettings.DOMAIN_ADDRESS + PATH + id;

            HttpConnectionService service = new HttpConnectionService();

            String response = service.sendRequest(linkedURL, postDataParams);

            try
            {
                if(!response.isEmpty())
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
                    ArrayList meetList = new ArrayList<>();

                    for(int i = 0; i < arrayJSON.length(); i++)
                    {
                        try
                        {
                            JSONObject objectJSON = arrayJSON.getJSONObject(i);

                            int idMeet = objectJSON.getInt("idMeet");
                            String dateMeet = objectJSON.getString("dateMeet");
                            String timeMeet = objectJSON.getString("timeMeet");
                            String locationMeet = objectJSON.getString("locationMeet");
                            int idClient = objectJSON.getInt("idClient");

                            CDataMeet cDataMeetItem = new CDataMeet(idMeet, dateMeet, timeMeet, locationMeet, idClient);

                            CItemListMeet cmeetListItem = new CItemListMeet(cDataMeetItem);

                            meetList.add(cmeetListItem);
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    RecyclerView.Adapter adapter = new CAdapterMeetList(meetList);

                    recyclerView = (RecyclerView) view.findViewById(R.id.rv_client_meet);

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getActivity()));
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }
}

