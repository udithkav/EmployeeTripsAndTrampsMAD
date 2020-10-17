package com.example.employeetripsandtramps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link finance_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class finance_details extends Fragment {
    EditText tid,uname,income,expense,profit;
    EditText tNumber;
    Button search;
    Button update, delete;
    DatabaseReference reff;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public finance_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment finance_details.
     */
    // TODO: Rename and change types and number of parameters
    public static finance_details newInstance(String param1, String param2) {
        finance_details fragment = new finance_details();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finance_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tid = (EditText) getView().findViewById(R.id.transaction_id);
        uname = (EditText) getView().findViewById(R.id.username);
        income = (EditText) getView().findViewById(R.id.income);
        expense = (EditText) getView().findViewById(R.id.exp);
        profit = (EditText) getView().findViewById(R.id.profit);
        search =(Button) getView().findViewById(R.id.search_report);
        update =(Button) getView().findViewById(R.id.button11);
        delete =(Button) getView().findViewById(R.id.button10);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinancialReportSearch frs = new FinancialReportSearch();
                frs.setTRANSECTION_ID(tid.getText().toString());
                reff = FirebaseDatabase.getInstance().getReference().child("finance");
                reff.child(frs.getTRANSECTION_ID()).removeValue();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap hashMap = new HashMap();
                reff = FirebaseDatabase.getInstance().getReference().child("finance");
                FinancialReportSearch fs = new FinancialReportSearch();

                fs.setUSERNAME(uname.getText().toString().trim());
                fs.setINCOME(income.getText().toString().trim());
                fs.setEXPENSES(expense.getText().toString().trim());
                fs.setPROFIT(profit.getText().toString().trim());
                fs.setTRANSECTION_ID(tid.getText().toString().trim());

                hashMap.put("expenses",fs.getEXPENSES());
                hashMap.put("income",fs.getINCOME());
                hashMap.put("profit",fs.getPROFIT());
                hashMap.put("username",fs.getUSERNAME());

                reff.child(fs.getTRANSECTION_ID()).updateChildren(hashMap);
                Toast.makeText(getActivity(),"Data Successfully Updated", Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff = FirebaseDatabase.getInstance().getReference().child("finance").child(tid.getText().toString());
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            FinancialReportSearch fins = new FinancialReportSearch();
                            fins.setPROFIT(snapshot.child("profit").getValue().toString());
                            fins.setEXPENSES(snapshot.child("expenses").getValue().toString());
                            fins.setINCOME(snapshot.child("income").getValue().toString());
                            fins.setUSERNAME(snapshot.child("username").getValue().toString());
                            uname.setText(fins.getUSERNAME());
                            income.setText(fins.getINCOME());
                            expense.setText(fins.getEXPENSES());
                            profit.setText(fins.getPROFIT());


                        }
                        else{
                            uname.setText("Inventory Doesn't Exist");
                            income.setText("Inventory Doesn't Exist");
                            expense.setText("Inventory Doesn't Exist");
                            profit.setText("Inventory Doesn't Exist");
                            Toast.makeText(getActivity(),"Inventory Doesn't Exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
}