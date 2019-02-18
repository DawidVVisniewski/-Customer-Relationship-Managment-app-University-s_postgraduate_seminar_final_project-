package com.example.damian.app_crm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentTabProfile extends Fragment
{
    private EditText etNameClient;
    private Spinner sTypeClient;
    private EditText etDescriptionClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tab_profile, container, false);

        etNameClient = (EditText) view.findViewById(R.id.et_name_client);
        sTypeClient = (Spinner) view.findViewById(R.id.s_type_client);
        etDescriptionClient = (EditText) view.findViewById(R.id.et_description_client);

        etNameClient.setKeyListener(null);
        etDescriptionClient.setKeyListener(null);

        setTypeField();
        setFormFields();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getActivity().getIntent().getStringExtra("nameClient"));
    }

    private void setFormFields()
    {
        etNameClient.setText(getActivity().getIntent().getStringExtra("nameClient"), TextView.BufferType.EDITABLE);

        switch(getActivity().getIntent().getStringExtra("typeClient"))
        {
            case "klient":

                sTypeClient.setSelection(1);

            break;

            case "perspektywa":

                sTypeClient.setSelection(0);

            break;

            case "sprzedawca":

                sTypeClient.setSelection(2);

            break;
        }

        sTypeClient.setEnabled(false);
        etDescriptionClient.setText(getActivity().getIntent().getStringExtra("descriptionClient"), TextView.BufferType.EDITABLE);
    }

    private void setTypeField()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.aTypeClient, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTypeClient.setAdapter(adapter);
    }
}
