package com.example.dawid.app_crm;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.damian.app_crm.output.HttpConnectionService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class ActivityLogin extends AppCompatActivity implements LoaderCallbacks<Cursor>
{
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String PATH = "account?";

    private UserLoginTask authTask = null;

    private AutoCompleteTextView tvLoginUser;
    private EditText etPassword;
    private Button bSignIn;
    private View vProgress;
    private View vLoginForm;

    private JSONArray arrayJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvLoginUser = (AutoCompleteTextView) findViewById(R.id.tv_login_user);
        etPassword = (EditText) findViewById(R.id.et_password);
        bSignIn = (Button) findViewById(R.id.b_sign_in);
        vLoginForm = findViewById(R.id.sv_login_form);
        vProgress = findViewById(R.id.vProgress);

        populateAutoComplete();

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if(id == R.id.login || id == EditorInfo.IME_NULL)
                {
                    attemptLogin();

                    return true;
                }

                return false;
            }
        });

        bSignIn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });
    }

    private void populateAutoComplete()
    {
        if (!mayRequestContacts())
        {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts()
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return true;
        }

        if(checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }

        if (shouldShowRequestPermissionRationale(READ_CONTACTS))
        {
            Snackbar.make(tvLoginUser, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener()
            {
                @Override
                @TargetApi(Build.VERSION_CODES.M)
                public void onClick(View v)
                {
                    requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                }
            });
        }
        else
        {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == REQUEST_READ_CONTACTS)
        {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                populateAutoComplete();
            }
        }
    }

    private void attemptLogin()
    {
        if (authTask != null)
        {
            return;
        }

        tvLoginUser.setError(null);
        etPassword.setError(null);

        String email = tvLoginUser.getText().toString();
        String password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(!TextUtils.isEmpty(password) && !isPasswordValid(password))
        {
            etPassword.setError(getString(R.string.error_invalid_password));

            focusView = etPassword;

            cancel = true;
        }

        if(TextUtils.isEmpty(email))
        {
            tvLoginUser.setError(getString(R.string.error_field_required));

            focusView = tvLoginUser;

            cancel = true;
        }
        else if(!isEmailValid(email))
        {
            tvLoginUser.setError(getString(R.string.error_invalid_email));

            focusView = tvLoginUser;

            cancel = true;
        }

        if(cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            showProgress(true);

            authTask = new UserLoginTask(email, password);
            authTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email)
    {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password)
    {
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)

    private void showProgress(final boolean show)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            vLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            vLoginForm.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    vLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            vProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            vProgress.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    vProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
        else
        {
            vProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            vLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new CursorLoader(this,
                                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI, ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
                                ProfileQuery.PROJECTION,
                                ContactsContract.Contacts.Data.MIMETYPE + " = ?", new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE},
                                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader){}

    private void addEmailsToAutoComplete(List<String> emailAddressCollection)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityLogin.this, android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        tvLoginUser.setAdapter(adapter);
    }


    private interface ProfileQuery
    {
        String[] PROJECTION = {ContactsContract.CommonDataKinds.Email.ADDRESS, ContactsContract.CommonDataKinds.Email.IS_PRIMARY,};

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean>
    {
        private String loginUserAT;
        private String passwordUserAT;

        public UserLoginTask(String loginUserAT, String passwordUserAT)
        {
            this.loginUserAT = loginUserAT;
            this.passwordUserAT = passwordUserAT;
        }

        private boolean downloadData(String loginUser, String passwordUser)
        {
            String login = "login=" + loginUser;
            String password = "password=" + passwordUser;

            HashMap<String, String> postDataParams = new HashMap<String, String>();

            postDataParams.put("HTTP_ACCEPT", "application/json");

            String linkedURL = CServerSettings.DOMAIN_ADDRESS + PATH + login + "&" + password;

            HttpConnectionService service = new HttpConnectionService();

            String response = service.sendRequest(linkedURL, postDataParams);

            boolean success;

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
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                return false;
            }

            return downloadData(loginUserAT, passwordUserAT);
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            authTask = null;

            showProgress(false);

            if(success)
            {
                finish();

                if(arrayJSON != null)
                {
                    try
                    {
                        if(arrayJSON.length() > 0)
                        {
                            JSONObject objectJSON = arrayJSON.getJSONObject(0);

                            Intent nextIntent = new Intent(ActivityLogin.this, ActivityMain.class);

                            nextIntent.putExtra("loginUser", objectJSON.getString("loginUser"));
                            nextIntent.putExtra("nameUser", objectJSON.getString("nameUser"));
                            nextIntent.putExtra("surnameUser", objectJSON.getString("surnameUser"));

                            startActivity(nextIntent);
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                etPassword.setError(getString(R.string.error_incorrect_password));
                etPassword.requestFocus();
            }
        }

        @Override
        protected void onCancelled()
        {
            authTask = null;

            showProgress(false);
        }
    }
}
