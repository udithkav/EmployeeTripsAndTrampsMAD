package com.example.employeetripsandtramps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        //Default Fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new EmployeeHome());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String userTypeRequiredHR = "Human Resource Manager";
        String userTypeRequiredIM = "Inventory Manager";
        String userTypeRequiredFM = "Finance Manager";
        String userTypeRequiredWF = "Workflow Manager";
        String currentUserType = null;

        sessionManager = new SessionManager(this);
        HashMap<String,String> user = sessionManager.getUserDetail();
        currentUserType = user.get("POSITION");

        if(menuItem.getItemId() ==R.id.home){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new EmployeeHome());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() ==R.id.addEmployee ){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new AddEmp());
            fragmentTransaction.commit();
        }

        if(menuItem.getItemId() ==R.id.editEmployee ){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new UpdateEmployeeDetails());
            fragmentTransaction.commit();
        }

        if(menuItem.getItemId() ==R.id.deleteEmployee ){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new DeleteEmployee());
            fragmentTransaction.commit();
        }

        if(menuItem.getItemId() ==R.id.viewEmployee){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new SearchEmployee());
            fragmentTransaction.commit();
        }

        if(menuItem.getItemId() ==R.id.addInventoryButton){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new AddInventory());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() ==R.id.InvButton){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new inventory_details());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() ==R.id.workAssignButton){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Work_Assign());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() ==R.id.otherWorkAssign){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Work_assign_details());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() ==R.id.createExpenseDetails){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new add_expenses_details());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() ==R.id.createIncomeDetails){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new AddIncomeDetails());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() ==R.id.createFinancialReport){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new financial_report());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() ==R.id.deleteUpdateExpenseDetails){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new expenses_details());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() ==R.id.deleteUpdateFinancialReport){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new finance_details());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() ==R.id.deleteUpdateIncomeDetails){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new income_details());
            fragmentTransaction.commit();
        }




        return true;
    }
}