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

public class FragmentTabNote extends Fragment
{
    private FloatingActionButton bAddNote;

    private String idClient;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tab_note, container, false);

        bAddNote = (FloatingActionButton) view.findViewById(R.id.b_add_note);

        bAddNote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ActivityMain activity = (ActivityMain) view.getContext();

                activity.setLastFragment("4");

                Fragment myFragment = new FragmentFormNote();

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.clCoordinator, myFragment).addToBackStack(null).commit();
            }
        });

        this.getIdClient();

        new ListNotesTask(view, this).execute();

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

    private class ListNotesTask extends AsyncTask<Void, Void, Boolean>
    {
        private static final String PATH = "note/list?";

        private View currentView;
        private Fragment fragment;
        private Context context;

        private ProgressDialog processDialog;
        private RecyclerView recyclerView;

        private boolean success;

        private JSONArray arrayJSON;

        public ListNotesTask(View currentView, Fragment fragment)
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

            String id = "id=" + idClient;

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
                    ArrayList noteList = new ArrayList<>();

                    for(int i = 0; i < arrayJSON.length(); i++)
                    {
                        try
                        {
                            JSONObject objectJSON = arrayJSON.getJSONObject(i);

                            int idNote = objectJSON.getInt("idNote");
                            String title = objectJSON.getString("titleNote");
                            String description = objectJSON.getString("descriptionNote");
                            int idClient = objectJSON.getInt("idClient");

                            CDataNote CDataNoteItem = new CDataNote(idNote, title, description, idClient);

                            CItemListNote cNoteListItem = new CItemListNote(CDataNoteItem);

                            noteList.add(cNoteListItem);
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    RecyclerView.Adapter adapter = new CAdapterNoteList(noteList);

                    recyclerView = (RecyclerView) currentView.findViewById(R.id.rv_client_note);

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getActivity()));
                    recyclerView.setAdapter(adapter);
                }
            }
        }
    }
}

