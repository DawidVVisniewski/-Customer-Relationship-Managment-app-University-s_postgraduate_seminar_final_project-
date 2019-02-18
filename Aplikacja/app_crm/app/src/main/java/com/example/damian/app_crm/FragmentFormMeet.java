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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentFormMeet extends Fragment implements View.OnClickListener
{
    private EditText etDateMeet;
    private EditText etTimeMeet;
    private EditText etLocationMeet;
    private Button bAddMeet;
    private Button bCancelMeet;

    private String date;
    private String time;
    private String location;
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
        currentView = inflater.inflate(R.layout.fragment_form_meet, container, false);

        etDateMeet = (EditText) currentView.findViewById(R.id.et_data_meet);
        etTimeMeet = (EditText) currentView.findViewById(R.id.et_time_meet);
        etLocationMeet = (EditText) currentView.findViewById(R.id.et_location_meet);

        bAddMeet = (Button) currentView.findViewById(R.id.b_add_meet);
        bCancelMeet = (Button) currentView.findViewById(R.id.b_cancel_meet);

        bAddMeet.setOnClickListener(this);
        bCancelMeet.setOnClickListener(this);

        return currentView;
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        if (id == R.id.b_add_meet)
        {
            date = etDateMeet.getText().toString();
            time = etTimeMeet.getText().toString();
            location = etLocationMeet.getText().toString();

            idClient = getActivity().getIntent().getStringExtra("idClient");

            boolean isValidData = true;

            if (!isValidDate(date))
            {
                etDateMeet.setError("Niepoprawny format wprowadzonych danych lub pole jest puste!");

                isValidData = false;
            }

            if (!isValidTime(time))
            {
                etTimeMeet.setError("Niepoprawny format wprowadzonych danych lub pole jest puste!");

                isValidData = false;
            }

            if (!isValidLocation(location))
            {
                etLocationMeet.setError("Pole lokalizacja nie może być puste!");

                isValidData = false;
            }

            if(isValidData)
            {
                new NewClientTask(this).execute();

                cancelDataEntry();
            }
        }
        else if(id == R.id.b_cancel_meet)
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

    private boolean isValidTime(String time)
    {
        if(time != null && time.equals("") == false)
        {
            final String TIME_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

            Pattern pattern = Pattern.compile(TIME_PATTERN);
            Matcher matcher = pattern.matcher(time);
            return matcher.matches();
        }
        else
        {
            return false;
        }
    }

    private boolean isValidLocation(String location)
    {
        if(time != null && time.equals("") == false)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isValidDate(String date)
    {
        if(date != null && !date.equals(""))
        {
            final String DATA_PATTERN = "yyyy-MM-dd";

            SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_PATTERN);

            dateFormat.setLenient(false);

            try
            {
                dateFormat.parse(date.trim());
            }
            catch(ParseException pe)
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    private class NewClientTask extends AsyncTask<Void, Void, Boolean>
    {
        private static final String PATH = "meet/add/";

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

            postDataParams.put("date", date);
            postDataParams.put("time", time);
            postDataParams.put("location", location);
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
                Toast.makeText(this.context, "Termin spotkania został dodany", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.context, "Termin spotkania nie został dodany", Toast.LENGTH_LONG).show();
            }
        }
    }
}
