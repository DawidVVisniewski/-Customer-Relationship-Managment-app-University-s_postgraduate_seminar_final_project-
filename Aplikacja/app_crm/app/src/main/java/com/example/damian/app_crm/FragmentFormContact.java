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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentFormContact extends Fragment implements View.OnClickListener
{
    private EditText etNameContact;
    private EditText etSurnameContact;
    private EditText etPhoneContact;
    private Button bAddContact;
    private Button bCancelContact;

    private String name;
    private String surname;
    private String phone;
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
        currentView = inflater.inflate(R.layout.fragment_form_contact, container, false);

        etNameContact = (EditText) currentView.findViewById(R.id.et_name_contact);
        etSurnameContact = (EditText) currentView.findViewById(R.id.et_surname_contact);
        etPhoneContact = (EditText) currentView.findViewById(R.id.et_phone_contact);

        bAddContact = (Button) currentView.findViewById(R.id.b_add_contact);
        bCancelContact = (Button) currentView.findViewById(R.id.b_cancel_contact);

        bAddContact.setOnClickListener(this);
        bCancelContact.setOnClickListener(this);

        return currentView;
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        if (id == R.id.b_add_contact)
        {
            name = etNameContact.getText().toString();
            surname = etSurnameContact.getText().toString();
            phone = etPhoneContact.getText().toString();

            idClient = getActivity().getIntent().getStringExtra("idClient");

            boolean isValidData = true;

            if (!isValidName(name))
            {
                etNameContact.setError("Niepoprawny format wprowadzonych danych lub pole jest puste!");

                isValidData = false;
            }

            if (!isValidSurname(surname))
            {
                etSurnameContact.setError("Niepoprawny format wprowadzonych danych lub pole jest puste!");

                isValidData = false;
            }

            if (!isValidPhone(phone))
            {
                etPhoneContact.setError("Niepoprawny format wprowadzonych danych lub pole jest puste!");

                isValidData = false;
            }

            if(isValidData)
            {
                new NewClientTask(this).execute();

                cancelDataEntry();
            }
        }
        else if(id == R.id.b_cancel_contact)
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
        if(name != null && name.equals("") == false)
        {
            final String NAME_PATTERN = "^[\\p{L} .'-]+$";

            Pattern pattern = Pattern.compile(NAME_PATTERN);
            Matcher matcher = pattern.matcher(name);
            return matcher.matches();
        }
        else
        {
            return false;
        }
    }

    private boolean isValidSurname(String surname)
    {
        if(surname != null && !surname.equals(""))
        {
            final String SURNAME_PATTERN = "^[\\p{L} .'-]+$";

            Pattern pattern = Pattern.compile(SURNAME_PATTERN);
            Matcher matcher = pattern.matcher(surname);
            return matcher.matches();
        }
        else
        {
            return false;
        }
    }

    private boolean isValidPhone(String phone)
    {
        if(phone != null && !phone.equals("") && phone.length() <= 9)
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
        private static final String PATH = "contact/add/";

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
            postDataParams.put("surname", surname);
            postDataParams.put("phone", phone);
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
                Toast.makeText(this.context, "Kontakt został dodany", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.context, "Kontakt nie został dodany", Toast.LENGTH_LONG).show();
            }
        }
    }
}
