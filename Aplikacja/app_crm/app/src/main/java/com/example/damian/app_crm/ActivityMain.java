package com.example.dawid.app_crm;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private Toolbar tToolbar;
    private DrawerLayout dlNavigation;
    private NavigationView nvNavigation;
    private TextView tvNameUser;
    private TextView tvLoginUser;

    private String lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tToolbar = (Toolbar) findViewById(R.id.t_toolbar);
        dlNavigation = (DrawerLayout) findViewById(R.id.dl_navigation);
        nvNavigation = (NavigationView) findViewById(R.id.nv_navigation);

        View viewNav =  nvNavigation.getHeaderView(0);

        tvNameUser = (TextView) viewNav.findViewById(R.id.tv_name_user);
        tvLoginUser = (TextView) viewNav.findViewById(R.id.tv_login_user);

        setSupportActionBar(tToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dlNavigation, tToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        dlNavigation.setDrawerListener(toggle);

        toggle.syncState();

        nvNavigation.setNavigationItemSelectedListener(this);

        serNavHeader();

        start_page();
    }

    private void start_page()
    {
        Fragment nextFragment = new FragmentMainClientList();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.clCoordinator, nextFragment);
        fragmentTransaction.commit();
    }

    private void serNavHeader()
    {
        String nameUser = getIntent().getStringExtra("nameUser");
        String surnameUser = getIntent().getStringExtra("surnameUser");
        String loginUser = getIntent().getStringExtra("loginUser");

        tvNameUser.setText(nameUser + " " + surnameUser);
        tvLoginUser.setText(loginUser);
    }

    @Override
    public void onBackPressed()
    {
        dlNavigation = (DrawerLayout) findViewById(R.id.dl_navigation);

        if(dlNavigation.isDrawerOpen(GravityCompat.START))
        {
            dlNavigation.closeDrawer(GravityCompat.START);
        }
        else
        {
            int fragments = getSupportFragmentManager().getBackStackEntryCount();

            if (fragments == 1)
            {
                finish();
            }
            else
            {
                if (getFragmentManager().getBackStackEntryCount() > 1)
                {
                    getFragmentManager().popBackStack();
                }
                else
                {
                    super.onBackPressed();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /*
    Obsługa poszczególnych pozycji w menu głównym aplikacji
     */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        Fragment nextFragment = null;

        int id = item.getItemId();

        if (id == R.id.i_nav_client)
        {
            nextFragment = new FragmentMainClientList();
        }
        else if(id == R.id.i_nav_log_out)
        {
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.i_nav_info)
        {
            nextFragment = new FragmentMainAboutSystem();
        }
        else if(id == R.id.i_nav_help)
        {
            nextFragment = new FragmentMainHelp();
        }

        if(nextFragment != null)
        {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.clCoordinator, nextFragment);
            fragmentTransaction.commit();
        }

        dlNavigation = (DrawerLayout) findViewById(R.id.dl_navigation);

        dlNavigation.closeDrawer(GravityCompat.START);

        return true;
    }

    public void setLastFragment(String lastFragment)
    {
        this.lastFragment = lastFragment;
    }

    public String getLastFragment()
    {
        return lastFragment;
    }
}
