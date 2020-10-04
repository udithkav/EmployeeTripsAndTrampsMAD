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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_expenses_details#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_expenses_details extends Fragment {
    EditText txtExpenseID, txtUSERNAME, txtAMOUNT, txtDETAILS, txtSearchExpNumber;
    Button butAdd,searchButton;
    DatabaseReference dbRef;
    DatabaseReference rrRef;
    expenses expense;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public add_expenses_details() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_expenses_details.
     */
    // TODO: Rename and change types and number of parameters
    public static add_expenses_details newInstance(String param1, String param2) {
        add_expenses_details fragment = new add_expenses_details();
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
        return inflater.inflate(R.layout.fragment_add_expenses_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtExpenseID = (EditText) getView().findViewById(R.id.expenseID);
        txtUSERNAME = (EditText)getView().findViewById(R.id.editname);
        txtAMOUNT = (EditText)getView().findViewById(R.id.editamount);
        txtDETAILS = (EditText)getView().findViewById(R.id.editdetails);
        txtSearchExpNumber = (EditText) getView().findViewById(R.id.editTextTextMultiLine2);
        butAdd = (Button)getView().findViewById(R.id.buttoninsert);
        searchButton = (Button)getView().findViewById(R.id.searchExpenseData);



        expense =new expenses();
        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Expense");

                try {
                    expense.setExpenseID(Integer.parseInt(txtExpenseID.getText().toString().trim()));
                    expense.setUsern(txtUSERNAME.getText().toString().trim());
                    expense.setAmountt(Double.parseDouble(txtAMOUNT.getText().toString().trim()));
                    expense.setDetails(txtDETAILS.getText().toString().trim());
                    Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_SHORT).show();
                    dbRef.child(String.valueOf(expense.getExpenseID())).setValue(expense);

                }catch(Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Data didn't get inserted", Toast.LENGTH_SHORT).show();
                }

            }
        });
       searchButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dbRef= FirebaseDatabase.getInstance().getReference().child("Expense").child(txtSearchExpNumber.getText().toString().trim());
               dbRef.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if(snapshot.exists()){
                           SearchExpenses expse = new SearchExpenses();
                           expse.setExpenseID(txtSearchExpNumber.getText().toString());
                           expse.setAmountt(snapshot.child("amountt").getValue().toString());
                           expse.setUsern(snapshot.child("usern").getValue().toString());
                           expse.setDetails(snapshot.child("details").getValue().toString());

                           txtExpenseID.setText(expse.getExpenseID());
                           txtUSERNAME.setText(expse.getUsern());
                           txtAMOUNT.setText(expse.getAmountt());
                           txtDETAILS.setText(expse.getDetails());


                       }
                       else{
                           txtExpenseID.setText("Expense doesnt not Exist");
                           txtUSERNAME.setText("Expense doesnt not Exist");
                           txtAMOUNT.setText("Expense doesnt not Exist");
                           txtDETAILS.setText("Expense doesnt not Exist");
                           Toast.makeText(getActivity(),"Expense doesnt not Exist!",Toast.LENGTH_LONG).show();
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