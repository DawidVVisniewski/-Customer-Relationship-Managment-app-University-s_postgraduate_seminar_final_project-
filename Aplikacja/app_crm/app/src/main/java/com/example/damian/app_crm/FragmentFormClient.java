package com.example.damian.app_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.damian.app_crm.output.HttpConnectionService;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class FragmentFormClient extends Fragment implements View.OnClickListener
{
    private EditText etNameClient;
    private Spinner sTypeClient;
    private EditText etDescriptionClient;
    private Button bAddClient;
    private Button bCancelClient;

    private String name;
    private String type;
    private String description;
    private String login;

    private View currentView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        currentView = inflater.inflate(R.layout.fragment_tab_profile, container, false);

        etNameClient = (EditText) currentView.findViewById(R.id.et_name_client);
        sTypeClient = (Spinner) currentView.findViewById(R.id.s_type_client);
        etDescriptionClient = (EditText) currentView.findViewById(R.id.et_description_client);

        bAddClient = (Button) currentView.findViewById(R.id.b_add_client);
        bCancelClient = (Button) currentView.findViewById(R.id.b_cancel_client);

        bAddClient.setVisibility(View.VISIBLE);
        bCancelClient.setVisibility(View.VISIBLE);

        bAddClient.setOnClickListener(this);
        bCancelClient.setOnClickListener(this);

        return currentView;
    }

    @Override
    public void onClick(View v)
    {
        int idView = v.getId();

        if (idView == R.id.b_add_client)
        {
            name = etNameClient.getText().toString();
            type = sTypeClient.getSelectedItem().toString();
            description = etDescriptionClient.getText().toString();

            login = getActivity().getIntent().getStringExtra("loginUser");

            boolean isValidData = true;

            if (!isValidName(name))
            {
                etNameClient.setError("Pole nazwa nie może być puste!");

                isValidData = false;
            }

            if (!isValidDescription(description))
            {
                etDescriptionClient.setError("Pole opis nie może być pusty!");

                isValidData = false;
            }

            if(isValidData)
            {
                new NewClientTask(this).execute();

                cancelDataEntry();
            }
        }
        else if(idView == R.id.b_cancel_client)
        {
            cancelDataEntry();
        }
    }

    private void cancelDataEntry()
    {
        if (getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
        }
    }

    private boolean isValidName(String name)
    {
        if(description != null && !description.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isValidDescription(String description)
    {
        if(description != null && !description.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private class NewClientTask extends AsyncTask<Void, Void, Boolean>
    {
        private static final String PATH = "client/add/";

        private Context context;

        private ProgressDialog processDialog;

        public NewClientTask(Fragment fragment)
        {
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

            HashMap<String, String> postDataParams = new HashMap<String, String>();

            String linkedURL = CServerSettings.DOMAIN_ADDRESS + PATH;

            postDataParams.put("name", name);
            postDataParams.put("type", type);
            postDataParams.put("description", description);
            postDataParams.put("login", login);

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
                Toast.makeText(this.context, "Klient został dodany", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.context, "Klient nie został dodany", Toast.LENGTH_LONG).show();
            }
        }
    }
}
