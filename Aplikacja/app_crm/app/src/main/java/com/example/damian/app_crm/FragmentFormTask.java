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
import android.widget.Toast;

import com.example.damian.app_crm.output.HttpConnectionService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class FragmentFormTask extends Fragment implements View.OnClickListener
{
    private EditText etTitleTask;
    private EditText etDescriptionTask;
    private Button bAddTask;
    private Button bCancelTask;

    private String title;
    private String description;
    private String flag;
    private String idClient;

    private View currentView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        currentView = inflater.inflate(R.layout.fragment_form_task, container, false);

        etTitleTask = (EditText) currentView.findViewById(R.id.et_title_task);
        etDescriptionTask = (EditText) currentView.findViewById(R.id.et_description_task);

        bAddTask = (Button) currentView.findViewById(R.id.b_add_task);
        bCancelTask = (Button) currentView.findViewById(R.id.b_cancel_task);

        bAddTask.setOnClickListener(this);
        bCancelTask.setOnClickListener(this);

        return currentView;
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        if (id == R.id.b_add_task)
        {
            title = etTitleTask.getText().toString();
            description = etDescriptionTask.getText().toString();

            flag = "0";

            idClient = getActivity().getIntent().getStringExtra("idClient");

            boolean isValidData = true;

            if (!isValidTitle(title))
            {
                etTitleTask.setError("Pole tytuł nie może być puste!");

                isValidData = false;
            }

            if(isValidData)
            {
                new NewClientTask(this).execute();

                cancelDataEntry();
            }
        }
        else if(id == R.id.b_cancel_task)
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

    private boolean isValidTitle(String title)
    {
        if(title != null && title.equals("") == false)
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
        private static final String PATH = "task/add/";

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

            postDataParams.put("title", title);
            postDataParams.put("description", description);
            postDataParams.put("flag", flag);
            postDataParams.put("id", idClient);

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
                Toast.makeText(this.context, "Zadanie zostało dodane", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.context, "Zadanie nie zostało dodane", Toast.LENGTH_LONG).show();
            }
        }
    }
}
