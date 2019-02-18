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

import java.util.HashMap;

public class FragmentFormNote extends Fragment implements View.OnClickListener
{
    private View view;

    private EditText etTitleNote;
    private EditText etDescriptionNote;
    private Button bAddNote;
    private Button bCancelNote;

    private String title;
    private String description;
    private String idClient;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_form_note, container, false);

        etTitleNote = (EditText) view.findViewById(R.id.et_title_note);
        etDescriptionNote = (EditText) view.findViewById(R.id.et_description_note);

        bAddNote = (Button) view.findViewById(R.id.b_add_note);
        bCancelNote = (Button) view.findViewById(R.id.b_cancel_note);

        bAddNote.setOnClickListener(this);
        bCancelNote.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        if (id == R.id.b_add_note)
        {
            title = etTitleNote.getText().toString();
            description = etDescriptionNote.getText().toString();
            idClient = getActivity().getIntent().getStringExtra("idClient");

            boolean isValid = true;

            if (!isValidTitle(title))
            {
                etTitleNote.setError("Błędne dane!");

                isValid = false;
            }

            if (!isValidDescription(description))
            {
                etDescriptionNote.setError("Błędne dane!");

                isValid = false;
            }

            if(isValid)
            {
                new NewClientTask(this).execute();

                if (getFragmentManager().getBackStackEntryCount() > 0)
                {
                    getFragmentManager().popBackStack();
                }
            }
        }
        else if(id == R.id.b_cancel_note)
        {
            if (getFragmentManager().getBackStackEntryCount() > 0)
            {
                getFragmentManager().popBackStack();
            }
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

    private class NewClientTask extends AsyncTask<Void, Void, Void>
    {
        private static final String PATH = "note/add/";

        private Context context;

        private ProgressDialog processDialog;

        private boolean success;

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
        protected Void doInBackground(Void... arg0)
        {
            HashMap<String, String> postDataParams = new HashMap<String, String>();

            String linkedURL = CServerSettings.DOMAIN_ADDRESS + PATH;

            postDataParams.put("title", title);
            postDataParams.put("description", description);
            postDataParams.put("id", idClient);

            HttpConnectionService service = new HttpConnectionService();

            service.sendRequest(linkedURL, postDataParams);

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (processDialog.isShowing())
            {
                processDialog.dismiss();
            }

            Toast.makeText(this.context, "Notatka została dodana", Toast.LENGTH_LONG).show();
        }
    }
}
