package com.example.damian.app_crm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentMainClientPanel extends Fragment
{
    private AppBarLayout ablBarClientPanel;
    private TabLayout tlTabClientPanel;
    private ViewPager vPanelClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_client_panel, container, false);

        View viewTemp = (View) container.getParent();

        ablBarClientPanel = (AppBarLayout) viewTemp.findViewById(R.id.abl_bar_client_panel);

        tlTabClientPanel = new TabLayout(getActivity());

        tlTabClientPanel.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));

        ablBarClientPanel.addView(tlTabClientPanel);

        vPanelClient = (ViewPager) view.findViewById(R.id.vPanel);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getFragmentManager());

        vPanelClient.setAdapter(pagerAdapter);

        tlTabClientPanel.setupWithViewPager(vPanelClient);
        tlTabClientPanel.setTabMode(TabLayout.MODE_SCROLLABLE);

        return view;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        ablBarClientPanel.removeView(tlTabClientPanel);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        String lastFragment = ((ActivityMain) getActivity()).getLastFragment();

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getFragmentManager());

        vPanelClient.setAdapter(pagerAdapter);

        if(lastFragment != null && !lastFragment.equals(""))
        {
            TabLayout.Tab tab = tlTabClientPanel.getTabAt(Integer.parseInt(lastFragment));

            tab.select();
        }
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter
    {
        private final String[] titleTab = {"Profil", "Kontakty", "Zadania", "Spotkania", "Notatki", "Aktywność"};

        public ViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new FragmentTabProfile();
                case 1:
                    return new FragmentTabContact();
                case 2:
                    return new FragmentTabTask();
                case 3:
                    return new FragmentTabMeet();
                case 4:
                    return new FragmentTabNote();
                case 5:
                    return new FragmentTabActivity();
            }

            return null;
        }

        @Override
        public int getCount()
        {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return titleTab[position];
        }
    }
}
