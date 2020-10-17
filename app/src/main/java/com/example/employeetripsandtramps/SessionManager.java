package com.example.employeetripsandtramps;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private  static final String PREF_NAME = "LOGIN";
    private static final String L_OGIN = "IS_LOGIN";
    private static final String F_IRSTNAME = "FIRSTNAME";
    private static final String L_ASTNAME = "LASTNAME";
    private static final String P_OSITION = "POSITION";
    private static final String D_OB = "DATEOFBIRTH";
    private static final String E_MAIL = "EMAIL";
    private static final String E_MPID = "EMPLOYEEID";
    private static final String N_IC = "NIC";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
    }
    public void createSession(String fName,String lName,String position,String dob,String email,String empID,String nic){
        editor.putBoolean(L_OGIN,true);
        editor.putString(F_IRSTNAME, fName);
        editor.putString(L_ASTNAME,lName);
        editor.putString(P_OSITION,position);
        editor.putString(D_OB,dob);
        editor.putString(E_MAIL,email);
        editor.putString(E_MPID,empID);
        editor.putString(N_IC,nic);
        editor.apply();

    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(F_IRSTNAME, sharedPreferences.getString(F_IRSTNAME, null));
        user.put(L_ASTNAME, sharedPreferences.getString(L_ASTNAME, null));
        user.put(P_OSITION, sharedPreferences.getString(P_OSITION, null));
        user.put(D_OB, sharedPreferences.getString(D_OB, null));
        user.put(E_MAIL, sharedPreferences.getString(E_MAIL, null));
        user.put(E_MPID,sharedPreferences.getString(E_MPID,null));
        user.put(N_IC,sharedPreferences.getString(N_IC,null));
        return user;

    }
    public void logout(){
        editor.clear();
        editor.commit();
    }
}
