package com.example.employeetripsandtramps;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.navigation.NavigationView;

public class HomePage<showmore> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout expandableview,expandableview1;
    TextView textView,textView1;
    CardView cardView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        expandableview = findViewById(R.id.expandable_view);
        textView = findViewById(R.id.showtext);
        expandableview1 = findViewById(R.id.expandable_view1);
        textView1 = findViewById(R.id.showtext1);
        cardView = findViewById(R.id.cardview_expandable);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

        

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showmore(View view) {

        if(expandableview.getVisibility()==view.GONE){
            textView.setText("Hide Profile");
            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
            expandableview.setVisibility(View.VISIBLE);
        }else
        {
            textView.setText("Profile");
            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
            expandableview.setVisibility(View.GONE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showmore1(View view) {

        if(expandableview1.getVisibility()==view.GONE){
            textView1.setText("Hide Job Details");
            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
            expandableview1.setVisibility(View.VISIBLE);
        }else
        {
            textView1.setText("Job Details");
            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
            expandableview1.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                break;
            case R.id.addEmployee:
                Intent intent = new Intent(this,AddEmployee.class);
                startActivity(intent);
                break;



        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}