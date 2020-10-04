package com.example.employeetripsandtramps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link financial_report#newInstance} factory method to
 * create an instance of this fragment.
 */
public class financial_report extends Fragment {
    EditText txtTid, txtUn, txtIncome, txtExpense,txtProfit,txtSearch;
    Button butAdd,butSearch;
    DatabaseReference dbRef;
    insert_finance finance;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public financial_report() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment financial_report.
     */
    // TODO: Rename and change types and number of parameters
    public static financial_report newInstance(String param1, String param2) {
        financial_report fragment = new financial_report();
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
        return inflater.inflate(R.layout.fragment_financial_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtTid = (EditText)getView().findViewById(R.id.editid);
        txtUn = (EditText)getView().findViewById(R.id.editname);
        txtIncome = (EditText)getView().findViewById(R.id.editincome);
        txtExpense = (EditText)getView().findViewById(R.id.editvehicle);
        txtProfit = (EditText)getView().findViewById(R.id.editprofit);
        txtSearch = (EditText) getView().findViewById(R.id.editTextTextMultiLine2);

        butAdd =(Button) getView().findViewById(R.id.save);
        butSearch = (Button)getView().findViewById(R.id.searchButtonFinancial);

        finance=new insert_finance();
        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("finance");

                try {
                    if (TextUtils.isEmpty(txtTid.getText().toString()))
                        Toast.makeText(getActivity(), "Empty Transectionid", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtUn.getText().toString()))
                        Toast.makeText(getActivity(), "Empty Username", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtIncome.getText().toString()))
                        Toast.makeText(getActivity(), "Empty Income", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtExpense.getText().toString()))
                        Toast.makeText(getActivity(), "Empty expenses", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtProfit.getText().toString()))
                        Toast.makeText(getActivity(), "Empty Profit", Toast.LENGTH_SHORT).show();
                    else {
                        finance.setTRANSECTION_ID(Integer.parseInt(txtTid.getText().toString().trim()));
                        finance.setUSERNAME(txtUn.getText().toString().trim());
                        finance.setINCOME(Integer.parseInt(txtIncome.getText().toString().trim()));
                        finance.setEXPENSES(Integer.parseInt(txtExpense.getText().toString().trim()));
                        finance.setPROFIT(Integer.parseInt(txtProfit.getText().toString().trim()));
                        dbRef.child(String.valueOf(finance.getTRANSECTION_ID())).setValue(finance);
                        Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        butSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef= FirebaseDatabase.getInstance().getReference().child("finance").child(txtSearch.getText().toString().trim());
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            FinancialReportSearch f1 = new FinancialReportSearch();
                            f1.setTRANSECTION_ID(txtSearch.getText().toString().trim());
                            f1.setPROFIT(snapshot.child("profit").getValue().toString());
                            f1.setEXPENSES(snapshot.child("expenses").getValue().toString());
                            f1.setINCOME(snapshot.child("income").getValue().toString());
                            f1.setUSERNAME(snapshot.child("username").getValue().toString());
                            txtTid.setText(f1.getTRANSECTION_ID());
                            txtUn.setText(f1.getUSERNAME());
                            txtIncome.setText(f1.getINCOME());
                            txtExpense.setText(f1.getEXPENSES());
                            txtProfit.setText(f1.getPROFIT());

                        }
                        else {
                            txtTid.setText("Financial Report Does not Exist");
                            txtUn.setText("Financial Report Does not Exist");
                            txtIncome.setText("Financial Report Does not Exist");
                            txtExpense.setText("Financial Report Does not Exist");
                            txtProfit.setText("Financial Report Does not Exist");
                            Toast.makeText(getActivity(),"Financial Report Does not Exist",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
    private void clearControls() {
        txtTid.setText("");
        txtUn.setText("");
        txtIncome.setText("");
        txtExpense.setText("");
        txtProfit.setText("");
    }
}