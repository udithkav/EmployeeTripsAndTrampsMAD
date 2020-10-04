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
 * Use the {@link AddIncomeDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddIncomeDetails extends Fragment {
    EditText txtINCOMEID, txtUSERNAME, txtROOMRESERVATION, txtVEHICLERESERVATION,txtPACKAGERESERVATION,searchData;
    Button butAdd,search;
    DatabaseReference dbRef;
    insert_income income;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddIncomeDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddIncomeDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static AddIncomeDetails newInstance(String param1, String param2) {
        AddIncomeDetails fragment = new AddIncomeDetails();
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
        return inflater.inflate(R.layout.fragment_add_income_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtINCOMEID =(EditText) getView().findViewById(R.id.editid);
        txtUSERNAME = (EditText)getView().findViewById(R.id.editname);
        txtROOMRESERVATION = (EditText)getView().findViewById(R.id.editincome);
        txtVEHICLERESERVATION = (EditText)getView().findViewById(R.id.editvehicle);
        txtPACKAGERESERVATION = (EditText)getView().findViewById(R.id.editpackage);
        searchData = (EditText)getView().findViewById(R.id.editTextTextMultiLine2);
        butAdd = (Button)getView().findViewById(R.id.buttoninsert);
        search = (Button)getView().findViewById(R.id.buttonSearch);
        income =new insert_income();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef= FirebaseDatabase.getInstance().getReference().child("income").child(searchData.getText().toString().trim());

                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            SearchIncome n1 = new SearchIncome();
                            n1.setiNCOMEID(searchData.getText().toString().trim());
                            n1.setuSERNAME(snapshot.child("username").getValue().toString());
                            n1.setvEHICLERESERVATION(snapshot.child("vehiclereservation").getValue().toString());
                            n1.setrOOMRESERVATION(snapshot.child("roomreservation").getValue().toString());
                            n1.setpACKAGERESERVATION(snapshot.child("packagereservation").getValue().toString());

                            txtINCOMEID.setText(n1.getiNCOMEID());
                            txtUSERNAME.setText(n1.getuSERNAME());
                            txtVEHICLERESERVATION.setText(n1.getvEHICLERESERVATION());
                            txtROOMRESERVATION.setText(n1.getrOOMRESERVATION());
                            txtPACKAGERESERVATION.setText(n1.getpACKAGERESERVATION());
                        }
                        else{
                            txtINCOMEID.setText("DOES NOT EXIST");
                            txtUSERNAME.setText("DOES NOT EXIST");
                            txtVEHICLERESERVATION.setText("DOES NOT EXIST");
                            txtROOMRESERVATION.setText("DOES NOT EXIST");
                            txtPACKAGERESERVATION.setText("DOES NOT EXIST");
                            Toast.makeText(getActivity(),"The Employee Number You Entered Does Not Exist!",Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("income");

                try {
                    if (TextUtils.isEmpty(txtINCOMEID.getText().toString()))
                        Toast.makeText(getActivity(), "Empty income id", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtUSERNAME.getText().toString()))
                        Toast.makeText(getActivity(), "Empty Username", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtROOMRESERVATION.getText().toString()))
                        Toast.makeText(getActivity(), "Empty Room Reservation", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtVEHICLERESERVATION.getText().toString()))
                        Toast.makeText(getActivity(), "Empty Vehicle Reservation", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtPACKAGERESERVATION.getText().toString()))
                        Toast.makeText(getActivity(), "Empty Package Reservation", Toast.LENGTH_SHORT).show();
                    else {
                        income.setINCOMEID(Integer.parseInt(txtINCOMEID.getText().toString().trim()));
                        income.setUSERNAME(txtUSERNAME.getText().toString().trim());
                        income.setROOMRESERVATION(Integer.parseInt(txtROOMRESERVATION.getText().toString().trim()));
                        income.setVEHICLERESERVATION(Integer.parseInt(txtVEHICLERESERVATION.getText().toString().trim()));
                        income.setPACKAGERESERVATION(Integer.parseInt(txtPACKAGERESERVATION.getText().toString().trim()));
                        dbRef.child(String.valueOf(income.getINCOMEID())).setValue(income);
                        Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void clearControls() {
        txtINCOMEID.setText("");
        txtUSERNAME.setText("");
        txtROOMRESERVATION.setText("");
        txtVEHICLERESERVATION.setText("");
        txtPACKAGERESERVATION.setText("");
    }
}